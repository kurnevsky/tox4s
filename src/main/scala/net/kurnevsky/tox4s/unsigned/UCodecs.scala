package net.kurnevsky.tox4s.unsigned

import scodec.Codec
import scodec.codecs.{byte, int32, int32L, int64, int64L, short16, short16L}

object UCodecs {
  val ubyte: Codec[UByte] =
    byte.xmap(UByte, _.toByte)
  val ushort: Codec[UShort] =
    short16.xmap(UShort, _.toShort)
  val ushortL: Codec[UShort] =
    short16L.xmap(UShort, _.toShort)
  val uint: Codec[UInt] =
    int32.xmap(UInt, _.toInt)
  val uintL: Codec[UInt] =
    int32L.xmap(UInt, _.toInt)
  val ulong: Codec[ULong] =
    int64.xmap(ULong, _.toLong)
  val ulongL: Codec[ULong] =
    int64L.xmap(ULong, _.toLong)
}
