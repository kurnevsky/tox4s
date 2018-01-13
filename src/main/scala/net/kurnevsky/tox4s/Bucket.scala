package net.kurnevsky.tox4s

import scala.collection.immutable.TreeSet
import scala.math.Ordering
import net.kurnevsky.tox4s.dht.rpc.PackedNode
import net.kurnevsky.tox4s.unsigned.UByte

final case class Bucket(
  publicKey: Array[Byte],
  capacity: UByte,
  nodes: TreeSet[PackedNode]
) {
  def add(node: PackedNode): Bucket =
    Bucket(
      publicKey,
      capacity,
      (nodes + node).take(capacity.toInt)
    )
  def size: Int =
    nodes.size
}

object Bucket {
  def empty(publicKey: Array[Byte], capacity: UByte = 8): Bucket = {
    implicit val ordering = new Ordering[PackedNode] {
      override def compare(node1: PackedNode, node2: PackedNode) =
        Distance.distance(publicKey, node1.publicKey, node2.publicKey)
    }
    Bucket(
      publicKey,
      capacity,
      TreeSet.empty
    )
  }
}
