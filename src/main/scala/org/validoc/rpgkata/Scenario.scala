package org.validoc.rpgkata

import java.util.concurrent.atomic.AtomicInteger

import Implicits._
import org.validoc.rpgkata.Utilities.{AnyPimper, FunctionPimper, KleisliPimper}

import scala.concurrent.ExecutionContext.Implicits._


class Scenario[T](damage: HitPoints => T => T, heal: HitPoints => T => T) {
  implicit def intToHitpoints(i: Int) = HitPoints(i)

  def scenario1 = damage(100) ~> damage(200) ~> damage(300) ~> heal(100) ~> damage(100)

  def scenario2 = damage(300) ~> damage(100) ~> heal(300) ~> heal(100) ~> damage(100)

}

object Scenario extends App {

  val rawDamage = implicitly[Damage[Character]]
  val rawHeal = implicitly[Heal[Character]]

  val recordDamage = new AtomicInteger()
  val recordHealing = new AtomicInteger()


  def damage(hitPoints: HitPoints) = Metrics(recordDamage, Logging("Damage {0} => {1}", rawDamage(hitPoints)))
  def heal(hitPoints: HitPoints) = Metrics(recordHealing, Logging("Healing {0} => {1}", rawHeal(hitPoints)))

  val scenarios = new Scenario[Character](damage, heal)
  import scenarios._

  println(s"We did ${recordDamage.get} calls to damage and ${recordHealing} healings")
  scenario1(Character("Thrud"))
  println(s"We did ${recordDamage.get} calls to damage and ${recordHealing} healings")

  import scenarios._

  val c1 = Character("c1")
  val c2 = Character("c2")


  //  c1.damage(100).flatMap(_.damage(200)).flatMap(_.damage(300)).flatMap(_.heal(100)).flatMap(_.damage(100)).foreach(println)
  //  c2.damage(300).flatMap(_.damage(100)).flatMap(_.heal(300)).flatMap(_.heal(100)).flatMap(_.damage(100)).foreach(println)
  //
  //
  //  (for {
  //    ca <- c1.damage(100)
  //    cb <- ca.damage(300)
  //    cc <- cb.damage(300)
  //    cd <- cc.heal(100)
  //    ce <- cd.damage(100)
  //  } yield {
  //    ce
  //  }).map(println)
  //
  //  val scenario1 = damage(100) ~~> damage(200) ~~> damage(300) ~~> heal(100) ~~> damage(100)
  //  val scenario2 = damage(300) ~~> damage(100) ~~> heal(300) ~~> heal(100) ~~> damage(100)
  //
  //  c1 |> (scenario1 ~> println)
  //  c2 |> (scenario2 ~> println)
  //
  //  Thread.sleep(100)
}
