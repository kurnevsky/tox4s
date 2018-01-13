package net.kurnevsky.tox4s.dht.rpc

import scodec.{Attempt, Codec, Err}
import net.kurnevsky.tox4s.representable.UByteRepresentable
import net.kurnevsky.tox4s.unsigned.{UByte, UCodecs}

sealed trait IpType extends UByteRepresentable

object IpType {
  case object U4 extends IpType {
    override def toUByte: UByte =
      2
  }
  case object U6 extends IpType {
    override def toUByte: UByte =
      10
  }
  case object T4 extends IpType {
    override def toUByte: UByte =
      130
  }
  case object T6 extends IpType {
    override def toUByte: UByte =
      138
  }

  val values: Set[IpType] =
    Set(U4, U6, T4, T6)

  def fromUByte(ubyte: UByte): Option[IpType] =
    values.find(_.toUByte == ubyte)

  val codec: Codec[IpType] =
    UCodecs.ubyte.narrow(
      ubyte ⇒ Attempt.fromOption(
        fromUByte(ubyte),
        Err.General(s"Can't parse $ubyte as IpType.", Nil)
      ),
      _.toUByte
    )
}
