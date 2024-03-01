package com.example.calculator

sealed class CalculatorAction {
    data class Number(val number: Int): CalculatorAction()
    data object Clear: CalculatorAction()
    data object Delete: CalculatorAction()
    data object Decimal: CalculatorAction()
    data object Calculate: CalculatorAction()
    data object Ounce: CalculatorAction()
    data object Bracket: CalculatorAction()
    data object ReduceFontSize: CalculatorAction()
    data class SetOunces(val ounces: Int): CalculatorAction()
    data class Operation(val operation: CalculatorOperation): CalculatorAction()
}