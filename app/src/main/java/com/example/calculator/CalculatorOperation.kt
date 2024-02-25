package com.example.calculator

sealed class CalculatorOperation(val symbol: String) {
    data object Add: CalculatorOperation("+")
    data object Minus: CalculatorOperation("-")
    data object Multiply: CalculatorOperation("x")
    data object Divide: CalculatorOperation("/")
}