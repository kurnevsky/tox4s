package net.kurnevsky.tox4s.unsigned

import scala.language.implicitConversions
import scala.math.Ordering

final class UInt(val toInt: Int) extends AnyVal {
  @inline
  private def flip: Int =
    toInt ^ Int.MinValue

  @inline
  def +(o: UInt): UInt =
    UInt(toInt + o.toInt)
  @inline
  def -(o: UInt): UInt =
    UInt(toInt - o.toInt)
  @inline
  def *(o: UInt): UInt =
    UInt(toInt * o.toInt)
  @inline
  def /(o: UInt): UInt =
    UInt(toInt / o.toInt)

  @inline
  def ^(o: UInt): UInt =
    UInt(toInt ^ o.toInt)
  @inline
  def &(o: UInt): UInt =
    UInt(toInt & o.toInt)
  @inline
  def |(o: UInt): UInt =
    UInt(toInt | o.toInt)

  @inline
  def compare(o: UInt): Int =
    flip compare o.flip

  @inline
  def <(o: UInt): Boolean =
    flip < o.flip
  @inline
  def <=(o: UInt): Boolean =
    flip <= o.flip
  @inline
  def >(o: UInt): Boolean =
    flip > o.flip
  @inline
  def >=(o: UInt): Boolean =
    flip >= o.flip

  @inline
  def toLong: Long =
    toInt.toLong & 0xFFFFFFFF
  @inline
  def toShort: Short =
    toInt.toShort
  @inline
  def toByte: Byte =
    toInt.toByte

  @inline
  def toULong: ULong =
    ULong(toLong)
  @inline
  def toUShort: UShort =
    UShort(toShort)
  @inline
  def toUByte: UByte =
    UByte(toByte)

  @inline
  def toDouble: Double =
    if (toInt >= 0) {
      toInt.toDouble
    } else {
      val div = (toInt >>> 1) / 5
      val mod = toInt - div * 10
      div.toDouble * 10 + mod
    }
  @inline
  def toFloat: Float =
    if (toInt >= 0) {
      toInt.toFloat
    } else {
      val div = (toInt >>> 1) / 5
      val mod = toInt - div * 10
      div.toFloat * 10 + mod
    }

  override def toString: String =
    if (toInt >= 0) {
      toInt.toString
    } else {
      val div = (toInt >>> 1) / 5
      val mod = toInt - div * 10
      div.toString + mod
    }
}

object UInt extends (Int ⇒ UInt) {
  @inline
  implicit def apply(byte: Byte): UInt =
    new UInt(byte)
  @inline
  implicit def apply(short: Short): UInt =
    new UInt(short)
  @inline
  implicit def apply(int: Int): UInt =
    new UInt(int)
  @inline
  implicit def apply(long: Long): UInt =
    new UInt(long.toInt)
}

object UIntOrdering extends Ordering[UInt] {
  override def compare(a: UInt, b: UInt): Int =
    a compare b
}
