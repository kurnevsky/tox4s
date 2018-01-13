package net.kurnevsky.tox4s

import cats.Monoid
import net.kurnevsky.tox4s.dht.rpc.PackedNode
import net.kurnevsky.tox4s.unsigned.UByte

final case class KBucket(
  publicKey: Array[Byte],
  k: UByte,
  buckets: Vector[Bucket]
) {
  def add(node: PackedNode): KBucket =
    KBucket.index(publicKey, node.publicKey) match {
      case Some(index) ⇒
        KBucket(
          publicKey,
          k,
          buckets.patch(
            from = index,
            patch = Seq(buckets(index).add(node)),
            replaced = 1
          )
        )
      case None ⇒
        this
    }
  def size: Int =
    buckets.map(_.size).sum
}

object KBucket {
  def index(key1: Array[Byte], key2: Array[Byte]): Option[Int] =
    (key1, key2).zipped.map(_ ^ _).zipWithIndex.find(_._1 != 0).map {
      case (xor, index) ⇒
        index * 8 + Integer.numberOfLeadingZeros(xor) - 24
    }

  def empty(k: UByte, publicKey: Array[Byte]): KBucket =
    KBucket(
      publicKey,
      k,
      (1 to k.toInt).map(_ ⇒ Bucket.empty(publicKey)).toVector
    )
}
