package com.example.calculator.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.calculator.data.CalculatorAction
import com.example.calculator.data.CalculatorState
import com.example.calculator.ui.theme.ButtonLightGrey

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorAction) -> Unit,
    haptic: HapticFeedback
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(30.dp, 20.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorTop(
                state = state,
                onAction = onAction,
                haptic = haptic
            )

            Divider(
                thickness = 1.dp,
                color = ButtonLightGrey
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CalculatorButtonScreen(
                    state = state,
                    onAction = onAction
                )
                CalculatorHistoryScreen(
                    state = state,
                    onAction = onAction,
                    haptic = haptic)
            }
        }
    }
}