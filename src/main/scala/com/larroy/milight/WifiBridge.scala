package com.larroy.milight

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.DatagramChannel

import java.util.concurrent.LinkedBlockingQueue

import scala.util.control.NonFatal

object WifiBridge {
  def apply(host: String, port: Int = 8899): WifiBridge = {
    new WifiBridge(host, port)
  }
}

class WifiBridge(host: String, port: Int, retries: Int = 3) extends Logging with AutoCloseable {
  val inetAddress = new InetSocketAddress(host, port)
  val channel = DatagramChannel.open()
  val commandQueue = new LinkedBlockingQueue[Command](50)
  def thread(body: ⇒ Unit): Thread = {
    val t = new Thread {
      override def run() = body
    }
    t.start()
    t
  }

  val consumerThread = thread {
    var interrupted = false
    while (! interrupted) {
      try {
        val nextCommand = commandQueue.take()
        send(nextCommand)
        log.debug(s"Sent command: $nextCommand")
        Thread.sleep(100)
      } catch {
        case e: InterruptedException ⇒
          log.info("Consumer thread interrupted")
          interrupted = true
        case NonFatal(e) ⇒
          log.error("Exception in consumer thread", e)
      }
    }
  }

  def close(): Unit = {
    consumerThread.interrupt()
    channel.close()
  }

  def nightMode(group: Int): Unit = {
    import CommandCode._
    group match {
      case 0 ⇒ queue(
        Command(ALL_OFF.id),
        Command(NIGHTMODE_ALL.id)
      )
      case x if (x >= 1 && x <= 4) ⇒ queue(
        Command(withName(s"GROUP_${x}_OFF").id),
        Command(withName(s"GROUP_${x}_NIGHTMODE").id)
      )
    }
  }

  def off(group: Int): Unit = {
    import CommandCode._
    group match {
      case 0 ⇒ queue(Command(ALL_OFF.id))
      case x if x >= 1 && x <= 4 ⇒ queue(Command(withName(s"GROUP_${x}_OFF").id))
    }
  }

  def white(group: Int): Unit = {
    import CommandCode._
    group match {
      case 0 ⇒ queue(Command(ALL_WHITE.id))
      case x if x >= 1 && x <= 4 ⇒ queue(Command(withName(s"GROUP_${x}_WHITE").id))
    }
  }


  def disco(group: Int): Unit = {
    import CommandCode._
    on(group)
    queue(Command(DISCO.id))
  }

  def on(group: Int): Unit = {
    import CommandCode._
    group match {
      case 0 ⇒ queue(Command(ALL_ON.id))
      case x if x >= 1 && x <= 4 ⇒ queue(Command(withName(s"GROUP_${x}_ON").id))
    }
  }

  def discoFaster(): Unit = {
    import CommandCode._
    queue(Command(DISCO_FASTER.id))
  }

  def discoSlower(): Unit = {
    import CommandCode._
    queue(Command(DISCO_SLOWER.id))
  }

  /**
    * @param group
    * @param color
    */
  def color(group: Int, col: Color): Unit = {
    col match {
      case Color(r, g, b) if (r == 0 && g == 0 && b == 0) ⇒ off(group)
      case Color(r, g, b) if (r == 255 && g == 255 && b == 255) ⇒ white(group)
      case c: Color ⇒
        val hue: Int = ColorConversion.to_hue(c)
        color(group, hue)
    }
  }

  def hue(group: Int, hue: Int): Unit = color(group, hue)

  def color(group: Int, hue: Int): Unit = {
    import CommandCode._
    on(group)
    queue(Command(COLOR.id, hue))
  }

  /**
    * @param group
    * @param level brightness from 0 to 1
    */
  def brightness(group: Int, level: Double): Unit = {
    import CommandCode._
    require(level <= 1.0)
    require(level >= 0.0)
    val iLevel = (CommandCode.MAX_BRIGHTNESS.id * level).toInt
    on(group)
    queue(Command(BRIGHTNESS.id, iLevel))
  }

  def queue(command: Command*): Unit = {
    command.foreach { commandQueue.put(_) }
  }

  private[milight] def send(command: Command): Unit = {
    send(command.toBytes)
  }

  private[milight] def send(bytesIn: Array[Byte]): Unit = {
    val bytes = pad(bytesIn)
    require(bytes.length == 3)
    var i = 0
    while (i < retries) {
      val sent = channel.send(ByteBuffer.wrap(bytes), inetAddress)
      if (sent != bytes.length)
        log.warn(s"short write, expected ${bytes.length} wrote $sent")
      i += 1
    }
  }

  private[milight] def pad(bytes: Array[Byte]): Array[Byte] = {
    require(bytes.length <= 2)
    bytes match {
      case Array(a) ⇒ Array(a, 0x00, 0x55)
      case Array(a, b) ⇒ Array(a, b, 0x55)
    }
  }
}
