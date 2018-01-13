package net.kurnevsky.tox4s

import net.kurnevsky.tox4s.unsigned.UByte

object Distance {
  def distance(key0: Array[Byte], key1: Array[Byte], key2: Array[Byte]): Int =
    key1.zip(key2).indexWhere {
      case (byte1, byte2) ⇒ byte1 != byte2
    } match {
      case -1 ⇒
        0
      case index ⇒
        val ubyte0 = UByte(key0(index))
        val ubyte1 = UByte(key1(index))
        val ubyte2 = UByte(key2(index))
        (ubyte0 ^ ubyte1) compare (ubyte0 ^ ubyte2)
    }
}
