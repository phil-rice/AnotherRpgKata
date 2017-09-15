package org.validoc.rpgkata

import Implicits._
import org.validoc.rpgkata.Utilities.{AnyPimper, FunctionPimper, KleisliPimper}
import scala.concurrent.ExecutionContext.Implicits._

class Scenario[T](implicit damage: Damage[T], heal: Heal[T]) {
  implicit def intToHitpoints(i: Int) = HitPoints(i)

  def scenario1 = damage(100) ~~> damage(200) ~~> damage(300) ~~> heal(100) ~~> damage(100)

  def scenario2 = damage(300) ~~> damage(100) ~~> heal(300) ~~> heal(100) ~~> damage(100)
}

object Scenario extends App {
  val scenarios = new Scenario[Character]
  val damage = implicitly[Damage[Character]]
  val heal = implicitly[Heal[Character]]

  import scenarios._

  val c1 = Character("c1")
  val c2 = Character("c2")

  c1.damage(100).flatMap(_.damage(200)).flatMap(_.damage(300)).flatMap(_.heal(100)).flatMap(_.damage(100)).foreach(println)
  c2.damage(300).flatMap(_.damage(100)).flatMap(_.heal(300)).flatMap(_.heal(100)).flatMap(_.damage(100)).foreach(println)


  (for {
    ca <- c1.damage(100)
    cb <- ca.damage(300)
    cc <- cb.damage(300)
    cd <- cc.heal(100)
    ce <- cd.damage(100)
  } yield {
    ce
  }).map(println)

  val scenario1 = damage(100) ~~> damage(200) ~~> damage(300) ~~> heal(100) ~~> damage(100)
  val scenario2 = damage(300) ~~> damage(100) ~~> heal(300) ~~> heal(100) ~~> damage(100)

  c1 |> (scenario1 ~> println)
  c2 |> (scenario2 ~> println)

  Thread.sleep(100)
}
