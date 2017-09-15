package org.validoc.rpgkata

case class HitPoints( hp: Int) {
  def +(that: HitPoints): HitPoints = HitPoints(hp + that.hp)
  def -(that: HitPoints): HitPoints = HitPoints(hp - that.hp)
  def lessThanZero: Boolean = hp < 0
  def moreThanMax: Boolean = hp>1000
}

case class Level(l: Int)

sealed trait LiveStatus

case object Alive extends LiveStatus

case object Dead extends LiveStatus

case class Character(name: String, level: Level = Level(1), alive: LiveStatus = Alive, hitPoints: HitPoints = HitPoints(1000))

case class Monster(name: String, level: Level = Level(1), alive: LiveStatus = Alive, hitPoints: HitPoints = HitPoints(1000))

object Character {

  implicit object DamageForCharacter extends Damage[Character] {
    override def apply(t: Character, hitPoints: HitPoints)(implicit dieIfNegativeHitPoints: DieIfNegativeHitPoints[Character]) = {
     dieIfNegativeHitPoints(t.copy(hitPoints = t.hitPoints - hitPoints))
    }
  }
  implicit object HealForCharacter extends Heal[Character] {
    override def apply(t: Character, hitPoints: HitPoints)(implicit capHealing: CapHealing[Character]) =
        capHealing(t.copy(hitPoints=hitPoints + t.hitPoints))
//        checkCanHeal ~>  doHealing ~> capHealing ~> recordTotalAmountHealed
  }

  implicit object DieIfNegativeHitPointsForCharacter extends DieIfNegativeHitPoints[Character] {
    override def apply(v1: Character) =
      if (v1.hitPoints.lessThanZero)
        v1.copy(hitPoints = HitPoints(0), alive = Dead)
      else v1
  }

  implicit object CapHealingForCharacter extends CapHealing[Character] {
    override def apply(v1: Character) = if (v1.hitPoints.moreThanMax) v1.copy(hitPoints = HitPoints(1000)) else v1
  }
}

trait DieIfNegativeHitPoints[T] extends (T => T)

trait Damage[T] {
  def apply(t: T, hitPoints: HitPoints)(implicit dieIfNegativeHitPoints: DieIfNegativeHitPoints[Character]): T
}
trait Heal[T] {
  def apply(t: T, hitPoints: HitPoints)(implicit capHealing: CapHealing[T]): T
}

trait PreHealingCheck[T] extends (T => Boolean)

trait CapHealing[T]extends (T => T)

object Implicits {

  implicit class CharacterPimper(character: Character)(implicit d: Damage[Character], h: Heal[Character]) {
    def damage(hitPoints: HitPoints) = d(character, hitPoints)
    def heal(hitPoints: HitPoints) = h(character, hitPoints)
  }

}


