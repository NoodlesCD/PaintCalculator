package com.example.calculator.data

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class CalculatorState(
    val values: ArrayList<String> = arrayListOf(),
    val input: String = "",
    val result: String = "",
    val operation: CalculatorOperation? = null,
    val ounceSetting: Int = 64,
    val openBrackets: Int = 0,
    val inputFontSize: TextUnit = 80.sp,
    val history: ArrayDeque<Triple<String, String, String>> = ArrayDeque(0),
    val historyVisible: Boolean = false,
)
