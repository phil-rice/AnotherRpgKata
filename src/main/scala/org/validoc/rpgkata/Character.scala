package org.validoc.rpgkata

case class Level(l: Int)

sealed trait LiveStatus

case object Alive extends LiveStatus

case object Dead extends LiveStatus

case class Character(name: String, level: Level = Level(1), alive: LiveStatus = Alive, hitPoints: HitPoints = HitPoints(1000))

case class Monster(name: String, level: Level = Level(1), alive: LiveStatus = Alive, hitPoints: HitPoints = HitPoints(1000))

object Character {

  implicit object ApplyHealingForCharacter extends ApplyHealing[Character] {
    override def apply(hitPoints: HitPoints) = (t => t.copy(hitPoints = hitPoints + t.hitPoints))
  }

  implicit object ApplyDamageForCharacter extends ApplyDamage[Character] {
    override def apply(v1: HitPoints) = { c: Character => c.copy(hitPoints = c.hitPoints - v1) }
  }

  implicit object KillIfNeededForCharacter$ extends KillIfNeeded[Character] {
    override def apply(v1: Character) =
      if (v1.hitPoints.lessThanZero)
        v1.copy(hitPoints = HitPoints(0), alive = Dead)
      else v1
  }

  implicit object CapHealingForCharacter extends CapHealing[Character] {
    override def apply(v1: Character) = if (v1.hitPoints.moreThanMax) v1.copy(hitPoints = HitPoints(1000)) else v1
  }

  implicit object CanHealForCharacter extends CanHeal[Character] {
    override def apply(v1: Character) = v1.alive == Alive
  }

}

object Implicits {


  implicit class CharacterPimper(character: Character) {
    def damage(hitPoints: HitPoints)(implicit d: Damage[Character]) = d(hitPoints)(character)

    def heal(hitPoints: HitPoints)(implicit h: Heal[Character]) = h(hitPoints)(character)
  }

}


