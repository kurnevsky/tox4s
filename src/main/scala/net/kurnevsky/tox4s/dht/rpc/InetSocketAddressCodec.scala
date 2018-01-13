package net.kurnevsky.tox4s.dht.rpc

import java.net.{Inet4Address, Inet6Address, InetAddress, InetSocketAddress}
import scodec.{Attempt, Codec, Err}
import net.kurnevsky.tox4s.unsigned.UCodecs
import net.kurnevsky.tox4s.ArrayCodecs

object InetSocketAddressCodec {
  val inet4Address: Codec[Inet4Address] =
    ArrayCodecs.array(4).xmap(
      array ⇒ InetAddress.getByAddress(array).asInstanceOf[Inet4Address],
      _.getAddress
    )
  val inet6Address: Codec[Inet6Address] =
    ArrayCodecs.array(16).xmap(
      array ⇒ InetAddress.getByAddress(array).asInstanceOf[Inet6Address],
      _.getAddress
    )
  val inetSocket4Address: Codec[InetSocketAddress] =
    (inet4Address ~ UCodecs.ushort).widen(
      { case (inetAddress, port) ⇒ new InetSocketAddress(inetAddress, port.toInt) },
      inetSocketAddress ⇒ inetSocketAddress.getAddress match {
        case inet4Address: Inet4Address ⇒
          Attempt.successful(inet4Address → inetSocketAddress.getPort)
        case inetAddress ⇒
          Attempt.failure(Err.General(s"Can't serialize $inetAddress as IPv4 address.", Nil))
      }
    )
  val inetSocket6Address: Codec[InetSocketAddress] =
    (inet6Address ~ UCodecs.ushort).widen(
      { case (inetAddress, port) ⇒ new InetSocketAddress(inetAddress, port.toInt) },
      inetSocketAddress ⇒ inetSocketAddress.getAddress match {
        case inet6Address: Inet6Address ⇒
          Attempt.successful(inet6Address → inetSocketAddress.getPort)
        case inetAddress ⇒
          Attempt.failure(Err.General(s"Can't serialize $inetAddress as IPv6 address.", Nil))
      }
    )
}
