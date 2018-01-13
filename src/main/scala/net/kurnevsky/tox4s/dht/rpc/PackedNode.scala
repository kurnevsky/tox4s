package net.kurnevsky.tox4s.dht.rpc

import com.emstlk.nacl4s.crypto.box.Curve25519XSalsa20Poly1305
import java.net.InetSocketAddress
import net.kurnevsky.tox4s.ArrayCodecs
import scodec.Codec

final case class PackedNode(
  ipType: IpType,
  socketAddress: InetSocketAddress,
  publicKey: Array[Byte]
)

object PackedNode {
  val codec: Codec[PackedNode] = (
    IpType.codec.flatZipHList {
      case IpType.U4 | IpType.T4 ⇒ InetSocketAddressCodec.inetSocket4Address
      case IpType.U6 | IpType.T6 ⇒ InetSocketAddressCodec.inetSocket6Address
    } :+ ArrayCodecs.array(Curve25519XSalsa20Poly1305.publicKeyBytes)
  ).as[PackedNode]
}
