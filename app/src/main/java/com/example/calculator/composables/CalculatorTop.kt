package com.example.calculator.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.data.CalculatorAction
import com.example.calculator.data.CalculatorState
import com.example.calculator.R
import com.example.calculator.ui.theme.ButtonGreen
import com.example.calculator.ui.theme.OunceBlue

@Composable
fun CalculatorTop(
    state: CalculatorState,
    onAction: (CalculatorAction) -> Unit,
    haptic: HapticFeedback
) {
    var shouldDraw by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            buildAnnotatedString {
                for (char in state.input) {
                    if (char in listOf('+', '-', 'x', '/')) {
                        withStyle(
                            style = SpanStyle(
                                color = ButtonGreen,
                                fontSize = state.inputFontSize
                            )
                        ) {
                            append(char)
                        }
                    } else if (char == 'y') {
                        withStyle(
                            style = SpanStyle(
                                color = OunceBlue,
                                fontSize = state.inputFontSize
                            )
                        ) {
                            append(char)
                        }
                    } else {
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontSize = state.inputFontSize
                            )
                        ) {
                            append(char)
                        }
                    }
                }
            },
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .drawWithContent {
                    if (shouldDraw) {
                        drawContent()
                    }
                },
            maxLines = 1, // modify to fit your need
            overflow = TextOverflow.Visible,
            onTextLayout = {
                if (it.hasVisualOverflow) {
                    onAction(CalculatorAction.ReduceFontSize)
                } else {
                    shouldDraw = true
                }
            }
        )
    }
    Text(
        text = state.result,
        textAlign = TextAlign.End,
        fontWeight = FontWeight.Light,
        fontSize = 40.sp,
        color = Color.Gray,
        maxLines = 2,
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            Icon(
                painter = painterResource(
                    id = if (state.historyVisible) {
                        R.drawable.action_calculator
                    } else {
                        R.drawable.action_history
                    }
                ),
                contentDescription = "",
                modifier = Modifier
                    .clickable(onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onAction(CalculatorAction.ToggleHistoryVisible)
                    })
                    .size(20.dp),
                tint = Color.White
            )
            Spacer(
                modifier = Modifier
                    .width(20.dp)
            )
            Text(
                text = "oz:",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 5.dp),
                color = Color.White
            )
            Text(
                text = "48",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickable(onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onAction(CalculatorAction.SetOunces(48))
                    }),
                color = if (state.ounceSetting == 48) {
                    Color.White
                } else {
                    Color.Gray
                }
            )
            Text(
                text = "64",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .clickable(onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        onAction(CalculatorAction.SetOunces(64))
                    }),
                color = if (state.ounceSetting == 64) {
                    Color.White
                } else {
                    Color.Gray
                }
            )
        }
        Text(
            text = "⌫",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clickable(onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    onAction(CalculatorAction.Delete)
                }),
            color = ButtonGreen
        )
    }
}