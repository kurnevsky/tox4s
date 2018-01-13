package net.kurnevsky.tox4s.representable

import net.kurnevsky.tox4s.unsigned.{UByte, UInt, ULong, UShort}

trait UByteRepresentable {
  def toUByte: UByte
}

trait UShortRepresentable {
  def toUShort: UShort
}

trait UIntRepresentable {
  def toUInt: UInt
}

trait ULongRepresentable {
  def toULong: ULong
}
