package com.example.calculator.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.data.CalculatorAction
import com.example.calculator.data.CalculatorState
import com.example.calculator.R
import com.example.calculator.ui.theme.ButtonGreen
import com.example.calculator.ui.theme.ButtonRed
import com.example.calculator.ui.theme.OunceBlue

@Composable
fun CalculatorHistoryScreen(
    state: CalculatorState,
    onAction: (CalculatorAction) -> Unit,
    haptic: HapticFeedback
) {
    Box {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            AnimatedVisibility(
                visible = state.historyVisible,
                enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                exit = slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 15.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "History",
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Light,
                        lineHeight = 30.sp,
                        fontSize = 30.sp,
                        color = Color.White,
                        maxLines = 1
                    )
                    Spacer(
                        modifier = Modifier
                            .width(20.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.action_delete_history),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable(onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                onAction(CalculatorAction.DeleteHistory)
                            })
                            .size(25.dp),
                        tint = ButtonRed
                    )
                }
                LazyColumn(
                    modifier = Modifier.padding(top = 50.dp)
                ) {
                    items(state.history) { historyItem ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 15.dp,
                                    horizontal = 10.dp
                                ),
                            horizontalArrangement = Arrangement.End,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.End,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        buildAnnotatedString {
                                            for (char in historyItem.first) {
                                                if (char in listOf(
                                                        '+',
                                                        '-',
                                                        'x',
                                                        '/'
                                                    )
                                                ) {
                                                    withStyle(
                                                        style = SpanStyle(
                                                            color = ButtonGreen,
                                                            fontSize = 23.sp
                                                        )
                                                    ) {
                                                        append(" $char ")
                                                    }
                                                } else if (char == 'y') {
                                                    withStyle(
                                                        style = SpanStyle(
                                                            color = OunceBlue,
                                                            fontSize = 23.sp
                                                        )
                                                    ) {
                                                        append(char)
                                                    }
                                                } else {
                                                    withStyle(
                                                        style = SpanStyle(
                                                            color = Color.White,
                                                            fontSize = 23.sp
                                                        )
                                                    ) {
                                                        append(char)
                                                    }
                                                }
                                            }
                                        },
                                        fontSize = 23.sp,
                                        maxLines = 2,
                                        color = Color.White,
                                        textAlign = TextAlign.End
                                    )
                                    Text(
                                        text = historyItem.second,
                                        fontSize = 27.sp,
                                        color = ButtonGreen
                                    )
                                }
                                Spacer(
                                    modifier = Modifier
                                        .width(20.dp)
                                )
                                Text(
                                    text = "1/${historyItem.third}",
                                    fontSize = 18.sp,
                                    color = Color.LightGray,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}