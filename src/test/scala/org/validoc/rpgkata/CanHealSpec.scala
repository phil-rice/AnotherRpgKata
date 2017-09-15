package org.validoc.rpgkata

class CanHealSpec extends KataSpec {

  import CharacterFixture._
  import HitPointsFixture._

  val canHeal = implicitly[CanHeal[Character]]

  behavior of "CanHeal"

  it should "allow alive characters to be healed" in {
    canHeal(thrud) shouldBe true
    canHeal(thrud0HitPoints) shouldBe true
    canHeal(thrudMinus100HitPoints) shouldBe true
  }
  it should "not allow dead characters to be healed" in {
    canHeal(deadThrud) shouldBe false
  }
}
