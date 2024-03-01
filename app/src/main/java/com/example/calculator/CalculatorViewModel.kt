package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.temobard.equationsolver.parsers.PostfixParser

class CalculatorViewModel: ViewModel() {

    /** Holds the current state of the calculator. */
    var state by mutableStateOf(CalculatorState())
        private set

    /** A CalculatorAction is passed when the user performs an action. */
    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Ounce -> enterOunce()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> performDeletion()
            is CalculatorAction.Bracket -> enterBracket()
            is CalculatorAction.ReduceFontSize -> reduceFontSize()
            is CalculatorAction.SetOunces -> setOunces(action.ounces)
            is CalculatorAction.Clear -> state = CalculatorState(ounces = state.ounces)
        }
    }

    /** Reduces the font size of the user's input when it overflows the screen. */
    private fun reduceFontSize() {
        state = state.copy(
            inputFontSize = state.inputFontSize * 0.95
        )
    }

    /**
     * When a user enters a number 0 - 9.
     * The input cannot exceed a length of 32.
     * The beginning digit or the first digit after an operation cannot be a 0.
     */
    private fun enterNumber(number: Int) {
        if (state.input.length >= MAX_NUM_LENGTH) return
        if (state.input.isBlank() && number == 0) return
        if (state.input.isNotBlank()) {
            if (state.input.last() in OPERATIONS && number == 0) return
        }

        state = state.copy(
            input = state.input + number
        )
        performCalculation(finalCalculation = false)
    }

    /**
     * Enters a decimal.
     * The first character cannot be a decimal.
     * The decimal can only be placed in certain indices in a number.
     */
    private fun enterDecimal() {
        if (state.input.isBlank()) return

        val isFirstNumber = !state.input.any { it in OPERATIONS }
        val currentNumber = if (isFirstNumber) {
            state.input
        } else {
            state.input.split(CURRENT_NUMBER_REGEX).last()
        }
        if (currentNumber.contains(".") || currentNumber.length !in VALID_DEC_INDEX) return

        state = state.copy(
            input = state.input + "."
        )
        return
    }

    /**
     * Enters a 'y' character to signify ounces.
     * Cannot be entered multiple times or after a decimal.
     * Cannot be placed in a number longer than 2 digits long.
     */
    private fun enterOunce() {
        if (state.input.isBlank()) return

        val isFirstNumber = !state.input.any { it in OPERATIONS }
        val currentNumber = if (isFirstNumber) {
            state.input
        } else {
            state.input.split(CURRENT_NUMBER_REGEX).last()
        }

        if (currentNumber.contains("y") ||
            currentNumber.contains(".") ||
            !(currentNumber.length == 1 || currentNumber.length == 2)
        ) {
            return
        }

        state = state.copy(
            input = state.input + "y"
        )
    }

    /**
     * Enters a opening or closing bracket.
     * Opening bracket will always be used if it is the first character.
     * The last character in the input cannot be a decimal.
     *
     * Open brackets must always have a closing bracket.
     * If the number of open brackets is 0, the bracket will always be an opening bracket.
     * Otherwise, an opening bracket will be used following an operation.
     * Or a closing bracket will be used following a digit.
     */
    private fun enterBracket() {
        if (state.input.isEmpty()) {
            state = state.copy (
                input = state.input + "(",
                openBrackets = 1
            )
            return
        }

        if (state.input.last() == '.') return

        if (state.openBrackets == 0) {
            state = state.copy (
                input = state.input + "(",
                openBrackets = 1
            )
        } else {
            state = if (state.input.last() in OPERATIONS) {
                state.copy (
                    input = state.input + "(",
                    openBrackets = state.openBrackets + 1
                )
            } else {
                state.copy (
                    input = state.input + ")",
                    openBrackets = state.openBrackets - 1
                )
            }
        }

        if (state.openBrackets == 0) performCalculation(finalCalculation = false)
    }

    /** Enters an operation such as plus or minus. */
    private fun enterOperation(operation: CalculatorOperation) {
        if (state.input.isNotBlank() && state.input.last() !in OPERATIONS) {
            state = state.copy (
                input = state.input + operation.symbol
            )
        }
    }

    /**
     * Performs a calculation when the input formula is valid or when = is pressed.
     * There can be no open brackets, all must have a closing bracket.
     * Input cannot be blank and the final character must be a digit or an ounce symbol.
     */
    private fun performCalculation(finalCalculation: Boolean = true) {
        if (state.input.isBlank()) return
        if (state.openBrackets > 0 || state.input.last() in OPERATIONS || state.input.last() == '.') {
            return
        }

        var equationString = state.input
        var startIndex = 0
        var endIndex = 1

        /** Searches through the input in order to convert formulas such as 1y2 into numerical equivalent. */
        while (endIndex <= equationString.length) {
            /** If the startIndex is an operation, continue to next index. */
            if (equationString[startIndex] in BRACKETS ||
                equationString[startIndex] in OPERATIONS
            ) {
                startIndex++
                endIndex++
                continue
            }

            /** If the endIndex is still part of the current number, move to the next index. */
            if (endIndex < equationString.length) {
                if (equationString[endIndex] !in OPERATIONS && equationString[endIndex] !in BRACKETS) {
                    endIndex++
                    continue
                }
            }

            /** Have reached the end of the current number, convert it into numerical equivalent. */
            val number = Formula(
                stringValue = equationString.substring(startIndex, endIndex),
                ounceSetting = state.ounces
            ).numericalStringValue()
            equationString = equationString.replaceRange(startIndex, endIndex, number)

            /** If we have reached the end of the input, break from the loop. */
            if (endIndex == equationString.length) break

            /** Move to the next indices to continue converting numbers. */
            startIndex += number.length + 1
            endIndex = startIndex + 1
        }

        /** Parse the string and calculate. */
        equationString = equationString.replace("x", "*")
        val equation = PostfixParser(equationString).parse()

        state = if (finalCalculation) {
            state.copy(
                input = Formula(
                    numericalValue = equation.calculate(),
                    ounceSetting = state.ounces
                ).stringValue,
                result = "",
                inputFontSize = 80.sp
            )
        } else {
            state.copy(
                result = Formula(
                    numericalValue = equation.calculate(),
                    ounceSetting = state.ounces
                ).stringValue
            )
        }
    }

    /**
     * Deletes the last character in the input.
     * Adjust quantity of openBrackets if necessary.
     */
    private fun performDeletion() {
        if (state.input.isBlank()) return

        if (state.input.last() == ')') {
            state = state.copy(
                openBrackets = state.openBrackets + 1
            )
        } else if (state.input.last() == '(') {
            state = state.copy(
                openBrackets = state.openBrackets - 1
            )
        }
        state = state.copy(
            input = state.input.dropLast(1)
        )

        if (state.input.isNotBlank()) {
            if (state.input.last() !in OPERATIONS ||
                state.input.last() != '.' ||
                state.openBrackets == 0) {
                performCalculation(finalCalculation = false)
            }
        } else {
            state = state.copy(
                result = ""
            )
        }
    }

    /** Sets the numerical value of an ounce. Either 48 or 64. */
    private fun setOunces(ounces: Int) {
        state = state.copy(
            ounces = ounces
        )
        performCalculation(finalCalculation = false)
    }

    companion object {
        private const val MAX_NUM_LENGTH = 32
        private val OPERATIONS = listOf('+', '-', 'x', '/')
        private val BRACKETS = listOf('(', ')')
        private val VALID_DEC_INDEX = listOf(1, 2, 3, 5, 6)
        private val CURRENT_NUMBER_REGEX = Regex("""[-\\+x]""")
    }
}