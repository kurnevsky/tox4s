package net.kurnevsky.tox4s.unsigned

import scala.language.implicitConversions
import scala.math.Ordering

final class ULong(val toLong: Long) extends AnyVal {
  @inline
  private def flip: Long =
    toLong ^ Long.MinValue

  @inline
  def +(o: ULong): ULong =
    ULong(toLong + o.toLong)
  @inline
  def -(o: ULong): ULong =
    ULong(toLong - o.toLong)
  @inline
  def *(o: ULong): ULong =
    ULong(toLong * o.toLong)
  @inline
  def /(o: ULong): ULong =
    ULong(toLong / o.toLong)

  @inline
  def ^(o: ULong): ULong =
    ULong(toLong ^ o.toLong)
  @inline
  def &(o: ULong): ULong =
    ULong(toLong & o.toLong)
  @inline
  def |(o: ULong): ULong =
    ULong(toLong | o.toLong)

  @inline
  def compare(o: ULong): Int =
    flip compare o.flip

  @inline
  def <(o: ULong): Boolean =
    flip < o.flip
  @inline
  def <=(o: ULong): Boolean =
    flip <= o.flip
  @inline
  def >(o: ULong): Boolean =
    flip > o.flip
  @inline
  def >=(o: ULong): Boolean =
    flip >= o.flip

  @inline
  def toInt: Int =
    toLong.toInt
  @inline
  def toShort: Short =
    toLong.toShort
  @inline
  def toByte: Byte =
    toLong.toByte

  @inline
  def toUInt: UInt =
    UInt(toInt)
  @inline
  def toUShort: UShort =
    UShort(toShort)
  @inline
  def toUByte: UByte =
    UByte(toByte)

  @inline
  def toDouble: Double =
    if (toLong >= 0) {
      toLong.toDouble
    } else {
      val div = (toLong >>> 1) / 5
      val mod = toLong - div * 10
      div.toDouble * 10 + mod
    }
  @inline
  def toFloat: Float =
    if (toLong >= 0) {
      toLong.toFloat
    } else {
      val div = (toLong >>> 1) / 5
      val mod = toLong - div * 10
      div.toFloat * 10 + mod
    }

  override def toString: String =
    if (toLong >= 0) {
      toLong.toString
    } else {
      val div = (toLong >>> 1) / 5
      val mod = toLong - div * 10
      div.toString + mod
    }
}

object ULong extends (Long ⇒ ULong) {
  @inline
  implicit def apply(byte: Byte): ULong =
    new ULong(byte)
  @inline
  implicit def apply(short: Short): ULong =
    new ULong(short)
  @inline
  implicit def apply(int: Int): ULong =
    new ULong(int)
  @inline
  implicit def apply(long: Long): ULong =
    new ULong(long)
}

object ULongOrdering extends Ordering[ULong] {
  override def compare(a: ULong, b: ULong): Int =
    a compare b
}
