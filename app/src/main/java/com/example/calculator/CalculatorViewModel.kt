package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.temobard.equationsolver.parsers.PostfixParser

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

    fun textFieldInput(string: String) {
        state = state.copy(
            input = string
        )
    }

    private fun enterNumber(number: Int) {
        if (state.input.length >= MAX_NUM_LENGTH) return

        state = state.copy(
            input = state.input + number
        )
        performCalculation(finalCalculation = false)
        return
    }

    // when calculating, use .replace to replace operations with ,+,
    private fun enterDecimal() {
        if (state.input.isBlank()) return

        var isFirstNumber = true
        for (operation in OPERATIONS) { if (state.input.contains(operation)) isFirstNumber = false }

        if (isFirstNumber && state.input.length !in VALID_DEC_INDEX) return
        if (!isFirstNumber) {
            val currentNumberAfterOperation = state.input.split(CURRENT_NUMBER_REGEX).last()
            if (currentNumberAfterOperation.contains(".") || currentNumberAfterOperation.length !in VALID_DEC_INDEX) return
        }

        state = state.copy(
            input = state.input + "."
        )
        return
    }

    private fun enterOunce() {
        if (state.input.isBlank()) return

        var isFirstNumber = true
        for (operation in OPERATIONS) { if (state.input.contains(operation)) isFirstNumber = false }

        if (isFirstNumber &&
            (state.input.contains("y") ||
                    state.input.contains(".") ||
                    !(state.input.length == 1 || state.input.length == 2))
        ) {
            return
        }

        if (!isFirstNumber) {
            val currentNumber = state.input.split(CURRENT_NUMBER_REGEX).last()
            if (currentNumber.contains("y") ||
                currentNumber.contains(".") ||
                !(currentNumber.length == 1 || currentNumber.length == 2)
            ) {
                return
            }
        }

        state = state.copy(
            input = state.input + "y"
        )
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.input.isNotBlank() && state.input.last() !in OPERATIONS) {
            state = state.copy (
                input = state.input + operation.symbol
            )
        }
    }

    private fun performCalculation(finalCalculation: Boolean = false) {
        if (state.input.isNotBlank() && state.input.last() !in OPERATIONS) {
            var equationString = state.input
            var startIndex = 0
            var endIndex = 1

            while (endIndex <= equationString.length) {
                if (endIndex == equationString.length) {
                    val number = Formula(
                        stringValue = equationString.substring(startIndex, endIndex)
                    ).numericalStringValue()
                    equationString = equationString.replaceRange(startIndex, endIndex, number)
                    break
                } else {
                    if (equationString[endIndex] in OPERATIONS) {
                        val number = Formula(
                            stringValue = equationString.substring(startIndex, endIndex)
                        ).numericalStringValue()
                        equationString = equationString.replaceRange(startIndex, endIndex, number)
                        startIndex += number.length + 1
                        endIndex = startIndex + 1
                    } else {
                        endIndex++
                    }
                }
            }

            equationString = equationString.replace("x", "*")
            val equation = PostfixParser(equationString).parse()

            state = if (finalCalculation) {
                state.copy(
                    input = "",
                    result = Formula(equation.calculate()).stringValue
                )
            } else {
                state.copy(
                    result = Formula(equation.calculate()).stringValue
                )
            }
        }
    }

    private fun performDeletion() {
        if (state.input.isNotBlank()) {
            state = state.copy(
                input = state.input.dropLast(1)
            )
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 24
        private val OPERATIONS = listOf('+', '-', 'x', '/')
        private val VALID_DEC_INDEX = listOf(1, 2, 5, 6)
        private val CURRENT_NUMBER_REGEX = Regex("""[-\\+x]""")
        private val OPERATIONS_REGEX = Regex("""[+\-/x]""")
    }
}