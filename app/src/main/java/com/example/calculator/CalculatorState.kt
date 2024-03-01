package com.example.calculator

data class CalculatorState(
    val values: ArrayList<String> = arrayListOf(),
    val input: String = "",
    val result: String = "",
    val operation: CalculatorOperation? = null
)
