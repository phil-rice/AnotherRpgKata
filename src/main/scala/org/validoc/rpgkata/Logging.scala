package org.validoc.rpgkata

import java.text.MessageFormat
import java.util.concurrent.atomic.AtomicInteger

import scala.collection.concurrent.TrieMap

trait LogData[T] extends (T => String)

object LogData {
  implicit def defaultLogData[T] = new LogData[T] {
    override def apply(v1: T) = v1.toString
  }

  implicit object CharacterLogData extends LogData[Character] {
    override def apply(v1: Character) = v1.name + " is alive: " + v1.alive + " with " + v1.hitPoints.hp
  }

}

/** example pattern would be 'damage {0} => {1}' */
class Logging[From, To](pattern: String, delegate: From => To)(implicit logDataF: LogData[From], logDataT: LogData[To]) extends (From => To) {
  override def apply(from: From) = {
    val result = delegate(from)
    println(MessageFormat.format(pattern, logDataF(from), logDataT(result)))
    result
  }
}

object Logging {
  def apply[From: LogData, To: LogData](pattern: String, delegate: From => To) = new Logging(pattern, delegate)

}


case class Metrics[From, To](store: AtomicInteger, delegate: From => To) extends (From => To) {
  override def apply(v1: From) = {
    val result = delegate(v1)
    store.incrementAndGet()
    result
  }
}
