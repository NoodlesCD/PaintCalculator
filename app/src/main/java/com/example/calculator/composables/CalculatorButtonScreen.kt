package com.example.calculator.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.calculator.data.CalculatorAction
import com.example.calculator.data.CalculatorOperation
import com.example.calculator.data.CalculatorState
import com.example.calculator.ui.theme.ButtonDarkGrey
import com.example.calculator.ui.theme.ButtonGreen
import com.example.calculator.ui.theme.ButtonLightGrey
import com.example.calculator.ui.theme.ButtonRed

@Composable
fun CalculatorButtonScreen(
    state: CalculatorState,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorAction) -> Unit,
) {
    Box {
        Column(
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            AnimatedVisibility(
                visible = !state.historyVisible,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        /** Top row */
                        CalculatorButton(
                            symbol = "AC",
                            color = ButtonRed,
                            modifier = Modifier
                                .aspectRatio(2f)
                                .weight(2f)
                                .background(ButtonLightGrey),
                            onClick = {
                                onAction(CalculatorAction.Clear)
                            }
                        )
                        CalculatorButton(
                            symbol = "( )",
                            color = ButtonGreen,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonLightGrey),
                            onClick = {
                                onAction(CalculatorAction.Bracket)
                            }
                        )
                        CalculatorButton(
                            symbol = "/",
                            color = ButtonGreen,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonLightGrey),
                            onClick = {
                                onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
                            }
                        )
                    }

                    /** 2nd row */
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(
                            symbol = "7",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(7))
                            }
                        )
                        CalculatorButton(
                            symbol = "8",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(8))
                            }
                        )
                        CalculatorButton(
                            symbol = "9",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(9))
                            }
                        )
                        CalculatorButton(
                            symbol = "x",
                            color = ButtonGreen,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonLightGrey),
                            onClick = {
                                onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
                            }
                        )
                    }

                    /** 3rd row */
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(
                            symbol = "4",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(4))
                            }
                        )
                        CalculatorButton(
                            symbol = "5",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(5))
                            }
                        )
                        CalculatorButton(
                            symbol = "6",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(6))
                            }
                        )
                        CalculatorButton(
                            symbol = "-",
                            color = ButtonGreen,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonLightGrey),
                            onClick = {
                                onAction(CalculatorAction.Operation(CalculatorOperation.Minus))
                            }
                        )
                    }

                    /** 4th row */
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(
                            symbol = "1",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(1))
                            }
                        )
                        CalculatorButton(
                            symbol = "2",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(2))
                            }
                        )
                        CalculatorButton(
                            symbol = "3",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(3))
                            }
                        )
                        CalculatorButton(
                            symbol = "+",
                            color = ButtonGreen,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonLightGrey),
                            onClick = {
                                onAction(CalculatorAction.Operation(CalculatorOperation.Add))
                            }
                        )
                    }

                    /** Bottom row */
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                    ) {
                        CalculatorButton(
                            symbol = "y",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Ounce)
                            }
                        )
                        CalculatorButton(
                            symbol = "0",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Number(0))
                            }
                        )
                        CalculatorButton(
                            symbol = ".",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonDarkGrey),
                            onClick = {
                                onAction(CalculatorAction.Decimal)
                            }
                        )
                        CalculatorButton(
                            symbol = "=",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .weight(1f)
                                .background(ButtonGreen),
                            onClick = {
                                onAction(CalculatorAction.Calculate)
                            }
                        )
                    }
                }
            }
        }
    }
}