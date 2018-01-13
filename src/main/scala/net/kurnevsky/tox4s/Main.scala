package net.kurnevsky.tox4s

import java.net.{InetAddress, InetSocketAddress}
import net.kurnevsky.tox4s.dht.{DhtPacket, PacketKind}
import net.kurnevsky.tox4s.dht.rpc._
import net.kurnevsky.tox4s.unsigned._
import com.emstlk.nacl4s.crypto.{Box, KeyPair}
import com.emstlk.nacl4s.crypto.box.Curve25519XSalsa20Poly1305
import cats._
import cats.data._
import cats.effect._
import fs2._
import fs2.io.udp
import fs2.io.udp.{AsynchronousSocketGroup, Packet, Socket}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random
import scodec.bits.BitVector
import scodec.Attempt

object Main extends App {
  def fromHex(s: String): Array[Byte] =
    s.sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toByte)

  val publicKey = fromHex("6FC41E2BD381D37E9748FC0E0328CE086AF9598BECC8FEB7DDF2E440475F300E")

  val keyPair = KeyPair()

  val precomputedKey = new Array[Byte](Curve25519XSalsa20Poly1305.beforenmBytes)
  Curve25519XSalsa20Poly1305.cryptoBoxBeforenm(precomputedKey, keyPair.publicKey, keyPair.privateKey)

  val address = new InetSocketAddress(InetAddress.getByName("51.15.37.145"), 33445)

  implicit val asynchronousSocketGroup = AsynchronousSocketGroup()
  val stream = udp.open[IO]()

  stream.evalMap { socket ⇒
    val nonce = Box.randomNonce()

    val requestId = ULong(Random.nextLong())

    val ping = Ping(PingType.Request, requestId)

    val dhtPacket = DhtPacket.pack(ping, keyPair, publicKey).require
    val dhtPacketBytes = DhtPacket.codec.encode(dhtPacket).require.bytes.toArray

    val packet = Packet(address, Chunk.bytes(dhtPacketBytes))

    for {
      _ ← socket.write(packet)
      responsePacket ← socket.read()
      responseDhtPacket = DhtPacket.codec.complete.decodeValue(BitVector(responsePacket.bytes.toArray)).require
      responseRpcPacket = responseDhtPacket.unpack(keyPair.privateKey).require
      _ ← IO {
        println(responseRpcPacket)
        Thread.sleep(1000)
      }
    } yield Unit
  }.repeat.take(10).run.unsafeRunSync
}
