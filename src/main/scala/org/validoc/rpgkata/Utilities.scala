package org.validoc.rpgkata

object Utilities {


  implicit class FunctionPimper[T, T1](fn: T => T1) {
    def ~>[T2](nextFn: T1 => T2) = fn andThen nextFn
  }

  implicit class BooleanFunctionPimper[T](fn: T => Boolean) {
    def ifTrue(guardedFunction: T => T) = { t: T => if (fn(t)) guardedFunction(t) else t }
  }

}
