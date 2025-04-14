package com.taycode.uikit.composables.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.taycode.uikit.R
import com.taycode.uikit.theme.UiKitTextStyle
import com.taycode.uikit.theme.UiKitTheme

@Composable
fun AppOutlineTextField(
    modifier: Modifier = Modifier,
    text: String,
    @StringRes hint: Int,
    singleLine: Boolean,
    enabled: Boolean,
    onTextChanged: (String) -> Unit,
) {
    var value by remember { mutableStateOf(text) }
    LaunchedEffect(text) {
        value = text
    }
    OutlinedTextField(
        modifier = modifier,
        textStyle = UiKitTextStyle.Body,
        value = TextFieldValue(
            text = value,
            selection = TextRange(value.length) // TextRange(0, textValue.length) -> Select that text in a color
        ),
        onValueChange = {
            onTextChanged.invoke(it.text)
        },
        label = {
            Text(
                stringResource(hint), style = UiKitTextStyle.Label
            ) // Usamos Label para la etiqueta
        },
        singleLine = singleLine,
        enabled = enabled,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        // readOnly = false,
    )
}

@Preview(showBackground = true)
@Composable
private fun AppOutlineTextFieldPreview() {
    UiKitTheme {
        var value by remember { mutableStateOf("") }
        TextFieldUiModel.Outlined(text = value, hint = R.string.select_an_option, onTextChanged = {
            value = it
        }).GetComposable()
    }
}