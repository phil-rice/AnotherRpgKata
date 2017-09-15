package org.validoc.rpgkata

import Utilities._

class Heal[T](implicit applyHealing: ApplyHealing[T], capHealing: CapHealing[T], canHeal: CanHeal[T]) {
  def apply(hitPoints: HitPoints) = canHeal ifTrue (applyHealing(hitPoints) andThen capHealing)
}

object Heal {
  implicit def heal[T](implicit applyHealing: ApplyHealing[T], capHealing: CapHealing[T], canHeal: CanHeal[T]) = new Heal[T]
}

trait ApplyHealing[T] extends (HitPoints => T => T)

trait PreHealingCheck[T] extends (T => Boolean)

trait CapHealing[T] extends (T => T)

trait CanHeal[T] extends (T => Boolean)

