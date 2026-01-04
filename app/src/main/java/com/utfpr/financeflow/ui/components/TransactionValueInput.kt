package com.utfpr.financeflow.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.utfpr.financeflow.ui.theme.FinanceFlowTheme

@Composable
fun TransactionValueInput(label: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= 8) { onValueChange(newValue) }
            },
            modifier = modifier.fillMaxWidth(),

            textStyle = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "R$",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 40.sp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    innerTextField()
                }
            }
        )
        Text(
            text = label,
            modifier = Modifier.padding(top = 8.dp),
            color = Color(0x947E817E),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun TransactionValueInputPreview() {
    FinanceFlowTheme {
        TransactionValueInput(
            label = "Valor",
            value = "125.00",
            onValueChange = {},
            modifier = Modifier
        )
    }
}