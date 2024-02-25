package com.example.calculator

class Formula(value: Int) {
    var ounceValue: Int = 64
    val numericalValue: Int = value
    val value: String = "${value / ounceValue}y${value % ounceValue}"

    constructor(value: Int, ounceValue: Int) : this(value) {
        this.ounceValue = ounceValue
    }

    operator fun plus(increment: Int): Formula {
        return Formula(numericalValue + increment)
    }

    operator fun minus(decrement: Int): Formula {
        return Formula(numericalValue - decrement)
    }

    operator fun times(multi: Int): Formula {
        return Formula(numericalValue * multi)
    }

    operator fun div(div: Int): Formula {
        return Formula(numericalValue / div)
    }
}