package net.kurnevsky.tox4s

import net.kurnevsky.tox4s.dht.rpc.PackedNode
import net.kurnevsky.tox4s.unsigned.ULong

case class Node(
  timeout: ULong,
  id: ULong,
  node: PackedNode
)
