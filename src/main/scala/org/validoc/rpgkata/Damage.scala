package org.validoc.rpgkata

import scala.concurrent.Future
import Utilities._
trait KillIfNeeded[T] extends (T => T)

trait ApplyDamage[T] extends (HitPoints => T => T)

class Damage[T](implicit applyDamage: ApplyDamage[T], dieIfNegativeHitPoints: KillIfNeeded[T]) extends (HitPoints => T => Future[T]){
  def apply(hitPoints: HitPoints) = applyDamage(hitPoints) andThen dieIfNegativeHitPoints toFutureFn
}

object Damage {
  implicit def damage[T](implicit applyDamage: ApplyDamage[T], dieIfNegativeHitPoints: KillIfNeeded[T]) = new Damage[T]
}
