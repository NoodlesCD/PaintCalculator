package com.example.calculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {

    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Ounce -> enterOunce()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Clear -> state = CalculatorState()
        }
    }

    private fun enterNumber(number: Int) {
        if (state.operation == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) return

            state = state.copy(
                number1 = state.number1 + number
            )
            return
        }

        if (state.number2.length >= MAX_NUM_LENGTH) return

        state = state.copy(
            number2 = state.number2 + number
        )
    }

    private fun enterDecimal() {
        if (state.operation == null
            && !state.number1.contains(".")
            && state.number1.length in listOf(1, 2, 5, 6)
            ) {
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }
        if (!state.number2.contains(".")
            && state.number2.length in listOf(1, 2, 5, 6)
            ) {
            state = state.copy(
                number2 = state.number2 + "."
            )
            return
        }
    }

    private fun enterOunce() {
        if (state.operation == null && !state.number1.contains("y")
            && !state.number1.contains(".")
            && (state.number1.length == 1 || state.number1.length == 2)) {
            state = state.copy(
                number1 = state.number1 + "y"
            )
            return
        }
        if (!state.number2.contains("y") && !state.number2.contains(".")
            && (state.number2.length == 1 || state.number2.length == 2)) {
            state = state.copy(
                number2 = state.number2 + "y"
            )
            return
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    private fun performCalculation() {
        val number1 = Formula(stringValue = state.number1)
        val number2 = Formula(stringValue = state.number2)

        val result = when (state.operation) {
            is CalculatorOperation.Add -> number1 + number2
            is CalculatorOperation.Minus -> number1 - number2
            is CalculatorOperation.Multiply -> {
                if (!state.number2.contains("y")) {
                    number1 * number2
                } else {
                    Formula(stringValue = "")
                }
            }
            is CalculatorOperation.Divide -> {
                if (!state.number2.contains("y")) {
                    number1 / number2
                } else {
                    Formula(stringValue = "")
                }
            }
            null -> return
        }

        if (result.stringValue.isNotEmpty()) {
            state = state.copy(
                number1 = result.stringValue,
                number2 = "",
                operation = null
            )
        }

    }

    private fun performDeletion() {
        when {
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )
            state.operation != null -> state = state.copy(
                operation = null
            )
            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}