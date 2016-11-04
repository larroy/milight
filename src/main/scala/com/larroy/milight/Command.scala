package com.larroy.milight

case class Command(code: Int*) {
  def toBytes: Array[Byte] = {
    code.map { x ⇒
      x.toByte
    }(collection.breakOut)
  }
}
