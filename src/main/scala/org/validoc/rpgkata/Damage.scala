package org.validoc.rpgkata

trait KillIfNeeded[T] extends (T => T)

trait ApplyDamage[T] extends (HitPoints => T => T)

class Damage[T](implicit applyDamage: ApplyDamage[T], dieIfNegativeHitPoints: KillIfNeeded[T]) {
  def apply(hitPoints: HitPoints) = applyDamage(hitPoints) andThen dieIfNegativeHitPoints
}

object Damage {
  implicit def damage[T](implicit applyDamage: ApplyDamage[T], dieIfNegativeHitPoints: KillIfNeeded[T]) = new Damage[T]
}
