package com.example.calculator

class Formula(
    numericalValue: Double = 0.0,
    stringValue: String = "",
) {
    private var ounceSetting: Int = 64
    private val numericalValue: Double = if (numericalValue > 0) {
        numericalValue
    } else {
        stringToNum(stringValue)
    }

    val stringValue: String = stringValue.ifBlank {
        when {
            numericalValue == 64.0 -> "${(numericalValue / ounceSetting).toInt()}y"
            numericalValue > 64 -> {
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

}