package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.ui.theme.ButtonDarkGrey
import com.example.calculator.ui.theme.ButtonGreen
import com.example.calculator.ui.theme.ButtonLightGrey
import com.example.calculator.ui.theme.ButtonRed
import com.example.calculator.ui.theme.CalcBackground
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.OunceBlue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state
                val buttonSpacing = 8.dp

                Calculator(
                    state = state,
                    onAction = viewModel::onAction,
                    buttonSpacing = buttonSpacing,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(CalcBackground)
                )
            }
        }
    }
}

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorAction) -> Unit,
) {
    var shouldDraw by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .padding(30.dp, 20.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Text(
                buildAnnotatedString {
                        for (char in state.input) {
                            if (char in listOf('+', '-', 'x', '/', )) {
                                withStyle(style = SpanStyle(color = ButtonGreen, fontSize = state.inputFontSize)) {
                                    append(char)
                                }
                            } else if (char == 'y') {
                                withStyle(style = SpanStyle(color = OunceBlue, fontSize = state.inputFontSize)) {
                                    append(char)
                                }
                            } else {
                                withStyle(style = SpanStyle(color = Color.White, fontSize = state.inputFontSize)) {
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
            Text(
                text = state.result,
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Light,
                fontSize = 40.sp,
                color = Color.Gray,
                maxLines = 2,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth()
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 30.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "oz:",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp),
                        color = Color.White
                    )
                    Text(
                        text = "48",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable { onAction(CalculatorAction.SetOunces(48)) },
                        color = if (state.ounces == 48) {
                            Color.White
                        } else {
                            Color.Gray
                        }
                    )
                    Text(
                        text = "64",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable { onAction(CalculatorAction.SetOunces(64)) },
                        color = if (state.ounces == 64) {
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
                        .clickable { onAction(CalculatorAction.Delete) },
                    color = ButtonGreen
                )
            }

            Divider(
                thickness = 1.dp,
                color = ButtonLightGrey
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
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

            Row(
                modifier = Modifier.fillMaxWidth(),
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

            Row(
                modifier = Modifier.fillMaxWidth(),
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

            Row(
                modifier = Modifier.fillMaxWidth(),
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
