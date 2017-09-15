package org.validoc.rpgkata

class ApplyDamageSpec extends KataSpec{
import CharacterFixture._
  import HitPointsFixture._
  behavior of "Apply Damage"

  val applyDamage = implicitly[ApplyDamage[Character]]

  it should "subtract hitpoints from the character" in {
      applyDamage(hp100)(thrud) shouldBe thrud900HitPoints
      applyDamage(hp100)(thrud0HitPoints) shouldBe thrudMinus100HitPoints
  }
}
