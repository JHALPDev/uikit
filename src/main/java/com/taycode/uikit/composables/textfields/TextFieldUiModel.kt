package com.taycode.uikit.composables.textfields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class TextFieldUiModel() {
    data class Outlined(val text: String, val hint: String, val singleLine: Boolean = true, val enabled: Boolean = true) :
        TextFieldUiModel()

    @Composable
    fun GetComposable(onTextChange: (String) -> Unit) {
        when (this) {
            is Outlined -> AppOutlineTextField(
                modifier = Modifier.fillMaxWidth(),
                text = text,
                hint = hint,
                singleLine = singleLine,
                enabled = enabled,
                onTextChange = onTextChange,
            )
        }
    }
}