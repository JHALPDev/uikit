package com.taycode.uikit.composables.textfields

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class TextFieldUiModel() {

    abstract val text: String

    data class Outlined(
        override val text: String,
        @StringRes val hint: Int,
        val singleLine: Boolean = true,
        val enabled: Boolean = true,
        val onTextChanged: (String) -> Unit
    ) : TextFieldUiModel()

    @Composable
    fun GetComposable(modifier: Modifier = Modifier) {
        when (this) {
            is Outlined -> AppOutlineTextField(
                modifier = modifier.fillMaxWidth(),
                text = text,
                hint = hint,
                singleLine = singleLine,
                enabled = enabled,
                onTextChanged = onTextChanged,
            )
        }
    }
}