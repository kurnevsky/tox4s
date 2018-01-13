package net.kurnevsky.tox4s.dht.rpc

import com.emstlk.nacl4s.crypto.box.Curve25519XSalsa20Poly1305
import net.kurnevsky.tox4s.ArrayCodecs
import net.kurnevsky.tox4s.dht.PacketKind
import net.kurnevsky.tox4s.unsigned.{UByte, UCodecs, ULong}
import scodec.{Codec, Decoder, Encoder}
import scodec.codecs.vectorOfN

sealed trait RpcPacket {
  val id: ULong
  def packetKind: PacketKind
}

object RpcPacket {
  val encoder: Encoder[RpcPacket] = Encoder {
    (_: RpcPacket) match {
      case ping: Ping ⇒
        Ping.codec.encode(ping)
      case nodesRequest: NodesRequest ⇒
        NodesRequest.codec.encode(nodesRequest)
      case nodesResponse: NodesResponse ⇒
        NodesResponse.codec.encode(nodesResponse)
    }
  }
  def decoder(packedKind: PacketKind): Decoder[RpcPacket] = Decoder { bitVector ⇒
    packedKind match {
      case PacketKind.PingRequest | PacketKind.PingResponse ⇒
        Ping.codec.decode(bitVector)
      case PacketKind.NodesRequest ⇒
        NodesRequest.codec.decode(bitVector)
      case PacketKind.NodesResponse ⇒
        NodesResponse.codec.decode(bitVector)
    }
  }
}

final case class Ping(
  pingType: PingType,
  id: ULong
) extends RpcPacket {
  override def packetKind: PacketKind =
    pingType match {
      case PingType.Request ⇒ PacketKind.PingRequest
      case PingType.Response ⇒ PacketKind.PingResponse
    }
}

object Ping {
  val codec: Codec[Ping] = (
    PingType.codec ::
      UCodecs.ulong
  ).as[Ping]
}

final case class NodesRequest(
  publicKey: Array[Byte],
  id: ULong
) extends RpcPacket {
  override def packetKind: PacketKind =
    PacketKind.NodesRequest
}

object NodesRequest {
  val codec: Codec[NodesRequest] = (
    ArrayCodecs.array(Curve25519XSalsa20Poly1305.publicKeyBytes) ::
      UCodecs.ulong
  ).as[NodesRequest]
}

final case class NodesResponse(
  nodes: Vector[PackedNode],
  id: ULong
) extends RpcPacket {
  override def packetKind: PacketKind =
    PacketKind.NodesResponse
}

object NodesResponse {
  val codec: Codec[NodesResponse] = (
    vectorOfN(UCodecs.ubyte.xmap(_.toInt, UByte.apply), PackedNode.codec) :: //TODO: safe
      UCodecs.ulong
  ).as[NodesResponse]
}
