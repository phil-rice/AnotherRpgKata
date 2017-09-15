package org.validoc.rpgkata

class DieIfNegativeHitPointsSpec extends KataSpec {

  import HitPointsFixture._
  import CharacterFixture._

  val dieIfNegativeHitPoints = implicitly[DieIfNegativeHitPoints[Character]]

  behavior of "DieIfNegativeHitPoints"

  it should "not kill the character with positive hitpoints " in {
    dieIfNegativeHitPoints(thrud) shouldBe thrud
    dieIfNegativeHitPoints(thrud100HitPoints) shouldBe thrud100HitPoints
    dieIfNegativeHitPoints(thrud0HitPoints) shouldBe thrud0HitPoints
  }

  it should "kill the character and set hitpoints to zero with negative hitpoints" in {
    dieIfNegativeHitPoints(thrudMinus1HitPoints) shouldBe thrudMinus1HitPoints.copy(alive = Dead, hitPoints = hp0)
    dieIfNegativeHitPoints(thrudMinus100HitPoints) shouldBe thrudMinus1HitPoints.copy(alive = Dead, hitPoints = hp0)

  }
}
