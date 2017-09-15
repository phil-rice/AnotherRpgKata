package org.validoc.rpgkata




case class HitPoints(hp: Int) {
  def +(that: HitPoints): HitPoints = HitPoints(hp + that.hp)

  def -(that: HitPoints): HitPoints = HitPoints(hp - that.hp)

  def lessThanZero: Boolean = hp < 0

  def moreThanMax: Boolean = hp > 1000
}
