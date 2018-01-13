package net.kurnevsky.tox4s.unsigned

import scala.language.implicitConversions
import scala.math.Ordering

final class UShort(val toShort: Short) extends AnyVal {
  @inline
  private def flip: Short =
    (toShort ^ Short.MinValue).toShort

  @inline
  def +(o: UShort): UShort =
    UShort(toShort + o.toShort)
  @inline
  def -(o: UShort): UShort =
    UShort(toShort - o.toShort)
  @inline
  def *(o: UShort): UShort =
    UShort(toShort * o.toShort)
  @inline
  def /(o: UShort): UShort =
    UShort(toShort / o.toShort)

  @inline
  def ^(o: UShort): UShort =
    UShort(toShort ^ o.toShort)
  @inline
  def &(o: UShort): UShort =
    UShort(toShort & o.toShort)
  @inline
  def |(o: UShort): UShort =
    UShort(toShort | o.toShort)

  @inline
  def compare(o: UShort): Int =
    flip compare o.flip

  @inline
  def <(o: UShort): Boolean =
    flip < o.flip
  @inline
  def <=(o: UShort): Boolean =
    flip <= o.flip
  @inline
  def >(o: UShort): Boolean =
    flip > o.flip
  @inline
  def >=(o: UShort): Boolean =
    flip >= o.flip

  @inline
  def toLong: Long =
    toShort.toLong & 0xFFFF
  @inline
  def toInt: Int =
    toShort & 0xFFFF
  @inline
  def toByte: Byte =
    toShort.toByte

  @inline
  def toULong: ULong =
    ULong(toLong)
  @inline
  def toUInt: UInt =
    UInt(toInt)
  @inline
  def toUByte: UByte =
    UByte(toByte)

  @inline
  def toDouble: Double =
    toInt.toDouble
  @inline
  def toFloat: Float =
    toInt.toFloat

  override def toString: String =
    toInt.toString
}

object UShort extends (Short ⇒ UShort) {
  @inline
  implicit def apply(byte: Byte): UShort =
    new UShort(byte)
  @inline
  implicit def apply(short: Short): UShort =
    new UShort(short)
  @inline
  implicit def apply(int: Int): UShort =
    new UShort(int.toShort)
  @inline
  implicit def apply(long: Long): UShort =
    new UShort(long.toShort)
}

object UShortOrdering extends Ordering[UShort] {
  override def compare(a: UShort, b: UShort): Int =
    a compare b
}
