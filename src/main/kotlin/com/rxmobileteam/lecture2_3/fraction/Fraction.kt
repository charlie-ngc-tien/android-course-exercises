package com.rxmobileteam.lecture2_3.fraction

import java.lang.IllegalArgumentException
import kotlin.math.roundToInt
import kotlin.math.roundToLong

fun calculateGCD(a: Int, b: Int): Int {
  var num1 = a
  var num2 = b
  while (num2 != 0) {
    val temp = num2
    num2 = num1 % num2
    num1 = temp
  }
  return num1
}

class Fraction private constructor(
  val numerator: Int,
  val denominator: Int,
) : Comparable<Fraction> {
  // TODO: Implement the decimal value of the fraction
  val decimal: Double = numerator.toDouble() / denominator

  init {
    // TODO: Check validity of numerator and denominator (throw an exception if invalid)
    if (denominator == 0) {
      throw IllegalArgumentException("denominator must be non zero.")
    }
  }

  //region unary operators
  // TODO: "+fraction" operator
  operator fun unaryPlus(): Fraction = Fraction(numerator, denominator)

  // TODO: "-fraction" operator
  operator fun unaryMinus(): Fraction = Fraction(-numerator, -denominator)
  //endregion

  //region plus operators
  // TODO: "fraction+fraction" operator
  operator fun plus(other: Fraction): Fraction = of(numerator * other.denominator + denominator * other.numerator, denominator * other.denominator)

  // TODO: "fraction+number" operator
  operator fun plus(other: Int): Fraction = of(numerator + denominator * other, denominator)
  //endregion

  //region times operators
  // TODO: "fraction*fraction" operator
  operator fun times(other: Fraction): Fraction = of(numerator * other.numerator, denominator * other.denominator)

  // TODO: "fraction*number" operator
  operator fun times(number: Int): Fraction = of(numerator * number, denominator)
  //endregion

  // TODO: Compare two fractions
  override fun compareTo(other: Fraction): Int {
    return when {
      this == other -> 0
      this.numerator * other.denominator > this.denominator * other.numerator -> 1
      else -> -1
    }
  }

  //region toString, hashCode, equals, copy
  // TODO: Format the fraction as a string (e.g. "1/2")
  override fun toString(): String = if (denominator == 1) "$numerator" else "$numerator/$denominator"

  // TODO: Implement hashCode
  override fun hashCode(): Int = decimal.hashCode()

  // TODO: Implement equals
  override fun equals(other: Any?): Boolean{
    if (other == null || other !is Fraction) {
      return false
    }
    return other.decimal == decimal
  }

  // TODO: Implement copy
  fun copy(
    numerator: Int = this.numerator,
    denominator: Int = this.denominator
  ): Fraction = Fraction(numerator, denominator)
  //endregion

  companion object {
    @JvmStatic
    fun ofInt(number: Int): Fraction {
      return Fraction(number, 1)
    }

    @JvmStatic
    fun of(numerator: Int, denominator: Int): Fraction {
      // TODO: Check validity of numerator and denominator
      // TODO: Simplify fraction using the greatest common divisor
      // TODO: Finally, return the fraction with the correct values
      if (denominator == 0) {
        throw IllegalArgumentException("denominator must be non zero.")
      }
      val gcd = calculateGCD(numerator, denominator)
      return Fraction(numerator / gcd, denominator / gcd)
    }
  }
}

// TODO: return a Fraction representing "this/denominator"
infix fun Int.over(denominator: Int): Fraction = Fraction.of(this, denominator)

//region get extensions
// TODO: get the numerator, eg. "val (numerator) = Fraction.of(1, 2)"
operator fun Fraction.component1(): Int = numerator

// TODO: get the denominator, eg. "val (_, denominator) = Fraction.of(1, 2)"
operator fun Fraction.component2(): Int = denominator

// TODO: get the decimal, index must be 0 or 1
// TODO: eg. "val numerator = Fraction.of(1, 2)[0]"
// TODO: eg. "val denominator = Fraction.of(1, 2)[1]"
// TODO: eg. "val denominator = Fraction.of(1, 2)[2]" should throw an exception
operator fun Fraction.get(index: Int): Int = when(index) {
  0 -> numerator
  1 -> denominator
  else -> throw IllegalAccessException("Invalid index")
}
//endregion

//region to number extensions
// TODO: round to the nearest integer
fun Fraction.roundToInt(): Int = decimal.roundToInt()

// TODO: round to the nearest long
fun Fraction.roundToLong(): Long = decimal.roundToLong()

// TODO: return the decimal value as a float
fun Fraction.toFloat(): Float = decimal.toFloat()

// TODO: return the decimal value as a double
fun Fraction.toDouble(): Double = decimal.toDouble()
//endregion

fun main() {
  // Creation
  println("1/2: ${Fraction.of(1, 2)}")
  println("2/3: ${Fraction.of(2, 3)}")
  println("8: ${Fraction.ofInt(8)}")
  println("2/4: ${2 over 4}")

  // Unary operators
  println("+2/4: ${+Fraction.of(2, 4)}")
  println("-2/6: ${-Fraction.of(2, 6)}")

  // Plus operators
  println("1/2 + 2/3: ${Fraction.of(1, 2) + Fraction.of(2, 3)}")
  println("1/2 + 1: ${Fraction.of(1, 2) + 1}")

  // Times operators
  println("1/2 * 2/3: ${Fraction.of(1, 2) * Fraction.of(2, 3)}")
  println("1/2 * 2: ${Fraction.of(1, 2) * 2}")

  // compareTo
  println("3/2 > 2/2: ${Fraction.of(3, 2) > Fraction.of(2, 2)}")
  println("1/2 <= 2/4: ${Fraction.of(1, 2) <= Fraction.of(2, 4)}")
  println("4/6 >= 2/3: ${Fraction.of(4, 6) >= Fraction.of(2, 3)}")

  // hashCode
  println("hashCode 1/2 == 2/4: ${Fraction.of(1, 2).hashCode() == Fraction.of(2, 4).hashCode()}")
  println("hashCode 1/2 == 1/2: ${Fraction.of(1, 2).hashCode() == Fraction.of(1, 2).hashCode()}")
  println("hashCode 1/3 == 3/5: ${Fraction.of(1, 3).hashCode() == Fraction.of(3, 5).hashCode()}")

  // equals
  println("1/2 == 2/4: ${Fraction.of(1, 2) == Fraction.of(2, 4)}")
  println("1/2 == 1/2: ${Fraction.of(1, 2) == Fraction.of(1, 2)}")
  println("1/3 == 3/5: ${Fraction.of(1, 3) == Fraction.of(3, 5)}")

  // Copy
  println("Copy 1/2: ${Fraction.of(1, 2).copy()}")
  println("Copy 1/2 with numerator 2: ${Fraction.of(1, 2).copy(numerator = 2)}")
  println("Copy 1/2 with denominator 3: ${Fraction.of(1, 2).copy(denominator = 3)}")
  println("Copy 1/2 with numerator 2 and denominator 3: ${Fraction.of(1, 2).copy(numerator = 2, denominator = 3)}")

  // Component1, Component2 operators
  val (numerator, denominator) = Fraction.of(1, 2)
  println("Component1, Component2 of 1/2: $numerator, $denominator")
  val (numerator2, _) = Fraction.of(10, 30)
  println("Component1 of 10/30: $numerator2")
  val (_, denominator2) = Fraction.of(10, 79)
  println("Component2 of 10/79: $denominator2")

  // Get operator
  println("Get 0 of 1/2: ${Fraction.of(1, 2)[0]}")
  println("Get 1 of 1/2: ${Fraction.of(1, 2)[1]}")
  println("Get 2 of 1/2: ${runCatching { Fraction.of(1, 2)[2] }}") // Should print "Failure(...)"

  // toInt, toLong, toFloat, toDouble
  println("toInt 1/2: ${Fraction.of(1, 2).roundToInt()}")
  println("toLong 1/2: ${Fraction.of(1, 2).roundToLong()}")
  println("toFloat 1/2: ${Fraction.of(1, 2).toFloat()}")
  println("toDouble 1/2: ${Fraction.of(1, 2).toDouble()}")

  // Range
  // Because we implemented Comparable<Fraction>, we can use Fraction in ranges
  val range = Fraction.of(1, 2)..Fraction.of(2, 3)
  println("1/2 in range 1/2..2/3: ${Fraction.of(1, 2) in range}") // "in" operator is contains
  println("2/3 in range 1/2..2/3: ${Fraction.of(2, 3) in range}")
  println("7/12 in range 1/2..2/3: ${Fraction.of(7, 12) in range}")
}
