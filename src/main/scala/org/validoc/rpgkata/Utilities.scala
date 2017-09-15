package org.validoc.rpgkata

object Utilities {

  implicit class BooleanFunctionPimper[T](fn: T => Boolean) {
    def ifTrue(ifTrue: T => T) = { t: T => if (fn(t)) ifTrue(t) else t }
  }

}
