package net.kurnevsky.tox4s.dht.rpc

import net.kurnevsky.tox4s.representable.UByteRepresentable
import net.kurnevsky.tox4s.unsigned.{UByte, UCodecs}
import scodec.{Attempt, Codec, Err}

sealed trait PingType extends UByteRepresentable

object PingType {
  case object Request extends PingType {
    override def toUByte: UByte =
      0
  }
  case object Response extends PingType {
    override def toUByte: UByte =
      1
  }

  val values: Set[PingType] =
    Set(Request, Response)

  def fromUByte(ubyte: UByte): Option[PingType] =
    values.find(_.toUByte == ubyte)

  val codec: Codec[PingType] =
    UCodecs.ubyte.narrow(
      ubyte ⇒ Attempt.fromOption(
        fromUByte(ubyte),
        Err.General(s"Can't parse $ubyte as PingType.", Nil)
      ),
      _.toUByte
    )
}
