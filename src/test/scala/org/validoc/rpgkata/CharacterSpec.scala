package org.validoc.rpgkata

import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}

trait KataSpec extends FlatSpec with Matchers with MockFactory

object CharacterFixture {
  import HitPointsFixture._
  val thrud = Character("Thrud")
  val deadThrud = Character("Thrud",alive = Dead, hitPoints = HitPoints(0))
  val thrud100HitPoints = Character("Thrud").copy(hitPoints = hp100)
  val thrud200HitPoints = Character("Thrud").copy(hitPoints = hp200)
  val thrud900HitPoints = Character("Thrud").copy(hitPoints = hp900)
  val thrud0HitPoints = Character("Thrud").copy(hitPoints = hp0)
  val thrudMinus1HitPoints = Character("Thrud").copy(hitPoints = hpMinus1)
  val thrudMinus100HitPoints = Character("Thrud").copy(hitPoints = hpMinus100)
}

class CharacterSpec extends KataSpec {

  import CharacterFixture._
  import HitPointsFixture._
  import Implicits._

  behavior of "Character"

  it should "Start with 1000 hitpoints by default" in {
    thrud.hitPoints shouldBe HitPoints(1000)
  }

  it should "start alive by default" in {
    thrud.alive shouldBe Alive
  }
  it should "start with level 1 by default" in {
    thrud.level shouldBe Level(1)
  }

  it should "received damage" in {
    thrud.damage(hp100) shouldBe thrud.copy(hitPoints = hp900)
  }

  it should "die if damage received takes it negative" in {
    thrud100HitPoints.damage(hp900) shouldBe deadThrud
  }

  it should "be healable" in {
    thrud100HitPoints.heal(hp100) shouldBe thrud200HitPoints
    thrud900HitPoints.heal(hp100) shouldBe thrud
  }

  it should "not be healable over maximum hitpoints " in {
    thrud.heal(hp100)shouldBe thrud
    thrud100HitPoints.heal(hp1000)shouldBe thrud
  }

  it should "not allow dead character to be healed" in {
    deadThrud.heal(hp100) shouldBe deadThrud
  }

}
