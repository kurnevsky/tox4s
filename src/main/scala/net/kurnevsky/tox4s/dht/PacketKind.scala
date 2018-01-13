package net.kurnevsky.tox4s.dht

import net.kurnevsky.tox4s.representable.UByteRepresentable
import net.kurnevsky.tox4s.unsigned.{UByte, UCodecs}
import scodec.{Attempt, Codec, Err}

sealed trait PacketKind extends UByteRepresentable

object PacketKind {
  case object PingRequest extends PacketKind {
    override def toUByte: UByte =
      0x00
  }
  case object PingResponse extends PacketKind {
    override def toUByte: UByte =
      0x01
  }
  case object NodesRequest extends PacketKind {
    override def toUByte: UByte =
      0x02
  }
  case object NodesResponse extends PacketKind {
    override def toUByte: UByte =
      0x04
  }
  case object CookieRequest extends PacketKind {
    override def toUByte: UByte =
      0x18
  }
  case object CookieResponse extends PacketKind {
    override def toUByte: UByte =
      0x19
  }
  case object CryptoHandshake extends PacketKind {
    override def toUByte: UByte =
      0x1a
  }
  case object CryptoData extends PacketKind {
    override def toUByte: UByte =
      0x1b
  }
  case object DHTRequest extends PacketKind {
    override def toUByte: UByte =
      0x20
  }
  case object LANDiscovery extends PacketKind {
    override def toUByte: UByte =
      0x21
  }
  case object OnionRequest0 extends PacketKind {
    override def toUByte: UByte =
      0x80
  }
  case object OnionRequest1 extends PacketKind {
    override def toUByte: UByte =
      0x81
  }
  case object OnionRequest2 extends PacketKind {
    override def toUByte: UByte =
      0x82
  }
  case object AnnounceRequest extends PacketKind {
    override def toUByte: UByte =
      0x83
  }
  case object AnnounceResponse extends PacketKind {
    override def toUByte: UByte =
      0x84
  }
  case object OnionDataRequest extends PacketKind {
    override def toUByte: UByte =
      0x85
  }
  case object OnionDataResponse extends PacketKind {
    override def toUByte: UByte =
      0x86
  }
  case object OnionResponse3 extends PacketKind {
    override def toUByte: UByte =
      0x8c
  }
  case object OnionResponse2 extends PacketKind {
    override def toUByte: UByte =
      0x8d
  }
  case object OnionResponse1 extends PacketKind {
    override def toUByte: UByte =
      0x8e
  }
  case object BootstrapInfo extends PacketKind {
    override def toUByte: UByte =
      0xf0
  }

  val values: Set[PacketKind] =
    Set(PingRequest, PingResponse, NodesRequest, NodesResponse,
      CookieRequest, CookieResponse, CryptoHandshake, CryptoData,
      DHTRequest, LANDiscovery, OnionRequest0, OnionRequest1,
      OnionRequest2, AnnounceRequest, AnnounceResponse,
      OnionDataRequest, OnionDataResponse, OnionResponse3,
      OnionResponse2, OnionResponse1, BootstrapInfo)

  def fromUByte(ubyte: UByte): Option[PacketKind] =
    values.find(_.toUByte == ubyte)

  val codec: Codec[PacketKind] =
    UCodecs.ubyte.narrow(
      ubyte ⇒ Attempt.fromOption(
        fromUByte(ubyte),
        Err.General(s"Can't parse $ubyte as PacketKind.", Nil)
      ),
      _.toUByte
    )
}
