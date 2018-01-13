package net.kurnevsky.tox4s

import scodec.Codec
import scodec.bits.ByteVector
import scodec.codecs.bytes

object ArrayCodecs {
  val array: Codec[Array[Byte]] =
    bytes.xmap(_.toArray, ByteVector.apply)
  def array(size: Int): Codec[Array[Byte]] =
    bytes(size).xmap(_.toArray, ByteVector.apply)
}
