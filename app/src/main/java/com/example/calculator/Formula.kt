package com.example.calculator

/**
 * A class to represent a paint formula.
 * Formulas are typically in a form such as 12y32.
 * 12y represents 12 ounces.
 * 32 represents either 32/48th or 32/64th of an ounce depending on [ounceSetting]
 * [numericalValue] converts each 1y into a value of either 48 or 64, plus the remaining fraction.
 * In the case of 1y10 with an [ounceSetting] of 64, this would result in 74. (64 + 10).
 *
 * [stringValue] represents the written formula (such as 12y32)
 * [ounceSetting] can either be 48 or 64, depending on the scenario.
 */
class Formula(
    numericalValue: Double = 0.0,
    stringValue: String = "",
    val ounceSetting: Int = 64,
) {
    private val numericalValue: Double = if (numericalValue > 0) {
        numericalValue
    } else {
        stringToNum(stringValue)
    }

    val stringValue: String = stringValue.ifBlank {
        when {
            numericalValue % ounceSetting.toDouble() == 0.0 -> "${(numericalValue / ounceSetting).toInt()}y"
            numericalValue > ounceSetting -> {
                if ((numericalValue % ounceSetting).toString().contains(".0")) {
                    "${(numericalValue / ounceSetting).toInt()}y${(numericalValue % ounceSetting).toInt()}"
                } else {
                    "${(numericalValue / ounceSetting).toInt()}y${numericalValue % ounceSetting}"
                }
            }
            else -> {
                if (numericalValue.toString().contains(".0")) {
                    "${numericalValue.toInt()}"
                } else {
                    "$numericalValue"
                }
            }
        }
    }

    operator fun plus(incrementBy: Formula): Formula {
        return Formula(numericalValue + incrementBy.numericalValue)
    }

    operator fun minus(decrementBy: Formula): Formula {
        return Formula(numericalValue - decrementBy.numericalValue)
    }

    operator fun times(multiplyBy: Formula): Formula {
        return Formula(numericalValue * multiplyBy.numericalValue )
    }

    operator fun div(divBy: Formula): Formula {
        return Formula(numericalValue / divBy.numericalValue)
    }

    private fun stringToNum(string: String): Double {
        return if (string.contains("y")) {
            if (string.substringAfter("y").isNotEmpty()) {
                (string.substringBefore("y").toDouble() * ounceSetting) +
                        string.substringAfter("y").toDouble()
            } else {
                string.substringBefore("y").toDouble() * ounceSetting
            }
        } else {
            string.toDouble()
        }
    }

    fun numericalStringValue(): String {
        return if (numericalValue.toString().contains(".0")) {
            "${numericalValue.toInt()}"
        } else {
            "$numericalValue"
        }
    }
}