package org.validoc.rpgkata

class CapHealingForCharacterSpec extends KataSpec {

  import CharacterFixture._
  import HitPointsFixture._

  val capHealing = implicitly[CapHealing[Character]]

  behavior of "DieIfNegativeHitPoints"

  it should "return character with maximum hitpoints if over maximum hitpoints" in {
    capHealing(thrud.copy(hitPoints = HitPoints(1001))) shouldBe thrud
  }

  it should "change the character if less than max points" in {
    capHealing(thrud) shouldBe thrud
    capHealing(thrud100HitPoints) shouldBe thrud100HitPoints
  }
}
