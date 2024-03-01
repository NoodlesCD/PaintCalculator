package com.example.calculator

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class CalculatorState(
    val values: ArrayList<String> = arrayListOf(),
    val input: String = "",
    val result: String = "",
    val operation: CalculatorOperation? = null,
    val ounces: Int = 64,
    val openBrackets: Int = 0,
    val inputFontSize: TextUnit = 80.sp,
)
