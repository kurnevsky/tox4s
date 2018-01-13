package net.kurnevsky.tox4s.unsigned

import scala.language.implicitConversions
import scala.math.Ordering

final class UByte(val toByte: Byte) extends AnyVal {
  @inline
  private def flip: Byte =
    (toByte ^ Byte.MinValue).toByte

  @inline
  def +(o: UByte): UByte =
    UByte(toByte + o.toByte)
  @inline
  def -(o: UByte): UByte =
    UByte(toByte - o.toByte)
  @inline
  def *(o: UByte): UByte =
    UByte(toByte * o.toByte)
  @inline
  def /(o: UByte): UByte =
    UByte(toByte / o.toByte)

  @inline
  def ^(o: UByte): UByte =
    UByte(toByte ^ o.toByte)
  @inline
  def &(o: UByte): UByte =
    UByte(toByte & o.toByte)
  @inline
  def |(o: UByte): UByte =
    UByte(toByte | o.toByte)

  @inline
  def compare(o: UByte): Int =
    flip compare o.flip

  @inline
  def <(o: UByte): Boolean =
    flip < o.flip
  @inline
  def <=(o: UByte): Boolean =
    flip <= o.flip
  @inline
  def >(o: UByte): Boolean =
    flip > o.flip
  @inline
  def >=(o: UByte): Boolean =
    flip >= o.flip

  @inline
  def toLong: Long =
    toByte.toLong & 0xFF
  @inline
  def toInt: Int =
    toByte & 0xFF
  @inline
  def toShort: Short =
    (toByte & 0xFF).toShort

  @inline
  def toULong: ULong =
    ULong(toLong)
  @inline
  def toUInt: UInt =
    UInt(toInt)
  @inline
  def toUShort: UShort =
    UShort(toShort)

  @inline
  def toDouble: Double =
    toInt.toDouble
  @inline
  def toFloat: Float =
    toInt.toFloat

  override def toString: String =
    toInt.toString
}

object UByte extends (Byte ⇒ UByte) {
  @inline
  implicit def apply(byte: Byte): UByte =
    new UByte(byte)
  @inline
  implicit def apply(short: Short): UByte =
    new UByte(short.toByte)
  @inline
  implicit def apply(int: Int): UByte =
    new UByte(int.toByte)
  @inline
  implicit def apply(long: Long): UByte =
    new UByte(long.toByte)
}

object UByteOrdering extends Ordering[UByte] {
  override def compare(a: UByte, b: UByte): Int =
    a compare b
}
