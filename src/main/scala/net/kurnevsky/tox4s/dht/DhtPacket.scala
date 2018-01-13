package net.kurnevsky.tox4s.dht

import com.emstlk.nacl4s.crypto.{Box, KeyPair}
import com.emstlk.nacl4s.crypto.box.Curve25519XSalsa20Poly1305
import net.kurnevsky.tox4s.ArrayCodecs
import net.kurnevsky.tox4s.dht.rpc.RpcPacket
import scodec.{Attempt, Codec}
import scodec.bits.BitVector

final case class DhtPacket(
  packetKind: PacketKind,
  senderBublicKey: Array[Byte],
  nonce: Array[Byte],
  payload: Array[Byte]
) {
  def unpack(privateKey: Array[Byte]): Attempt[RpcPacket] = {
    val decryptedPayload = Box(senderBublicKey, privateKey).decrypt(nonce, payload)
    RpcPacket.decoder(packetKind).complete.decodeValue(BitVector(decryptedPayload))
  }
}

object DhtPacket {
  /// Number of bytes in the authenticator tag of an encrypted message
  /// i.e. the number of bytes by which the ciphertext is larger than the
  /// plaintext.
  val macBytes: Int =
    Curve25519XSalsa20Poly1305.zeroBytes - Curve25519XSalsa20Poly1305.boxZeroBytes

  def pack(rpcPacket: RpcPacket, keyPair: KeyPair, publicKey: Array[Byte]): Attempt[DhtPacket] =
    for (payload ← RpcPacket.encoder.encode(rpcPacket).map(_.bytes.toArray)) yield {
      val nonce = Box.randomNonce()
      val encryptedPayload = Box(publicKey, keyPair.privateKey).encrypt(nonce, payload)
      DhtPacket(
        packetKind = rpcPacket.packetKind,
        senderBublicKey = keyPair.publicKey,
        nonce = nonce,
        payload = encryptedPayload
      )
    }

  val codec: Codec[DhtPacket] = (
    PacketKind.codec ::
      ArrayCodecs.array(Curve25519XSalsa20Poly1305.publicKeyBytes) ::
      ArrayCodecs.array(Curve25519XSalsa20Poly1305.nonceBytes) ::
      ArrayCodecs.array
  ).as[DhtPacket]
}
