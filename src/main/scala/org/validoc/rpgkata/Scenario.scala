package org.validoc.rpgkata

import Implicits._
import org.validoc.rpgkata.Utilities.FunctionPimper

class Scenario[T](implicit damage: Damage[T], heal: Heal[T]) {
  implicit def intToHitpoints(i: Int) = HitPoints(i)

  def scenario1 = damage(100) ~> damage(200) ~> damage(300) ~> heal(100) ~> damage(100)

  def scenario2 = damage(300) ~> damage(100) ~> heal(300) ~> heal(100) ~> damage(100)

}

object Scenario extends App {
  val scenarios = new Scenario[Character]

  import scenarios._


  val c1 = Character("c1")
  val c2 = Character("c2")

  println(c1.damage(100).damage(200).damage(300).heal(100).damage(100))
  println(c2.damage(300).damage(100).heal(300).heal(100).damage(100))


  println(scenario1(c1))
  println(scenario2(c2))
}
