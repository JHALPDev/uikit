package com.taycode.uikit.composables.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taycode.uikit.theme.UiKitTextStyle
import com.taycode.uikit.theme.UiKitTheme

sealed class ButtonUiModel {
    data class AppSimpleButton(
        val text: String,
        val enabled: Boolean = true,
        val onClick: () -> Unit,
    ) : ButtonUiModel()

    data class AppOutlinedButton(
        val text: String,
        val enabled: Boolean = true,
        val onClick: () -> Unit,
    ) : ButtonUiModel()

    data class AppTextButton(
        val text: String,
        val enabled: Boolean = true,
        val onClick: () -> Unit,
    ) : ButtonUiModel()

    @Composable
    fun GetComposable(modifier: Modifier = Modifier) {
        when (this) {
            is AppSimpleButton -> AppSimpleButton(
                text = text,
                enabled = enabled,
                onClick = onClick,
                modifier = modifier.fillMaxWidth()
            )

            is AppOutlinedButton -> AppOutlinedButton(
                text = text,
                enabled = enabled,
                onClick = onClick,
                modifier = modifier.fillMaxWidth()
            )

            is AppTextButton -> AppTextButton(
                text = text,
                enabled = enabled,
                onClick = onClick,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun AppTextButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary, // colore del testo
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f) // standard Material
        )
    ) {
        Text(
            text = text, style = UiKitTextStyle.Button
        )
    }
}

@Composable
fun AppSimpleButton(text: String, enabled: Boolean, onClick: () -> Unit, modifier: Modifier) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        )
    ) {
        Text(
            text = text, style = UiKitTextStyle.Button
        )
    }
}

@Composable
fun AppOutlinedButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary // colore del testo
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary), // colore del bordo
    ) {
        Text(
            text = text, style = UiKitTextStyle.Button
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleButtonPreview() {
    UiKitTheme {
        Column {
            ButtonUiModel.AppSimpleButton(text = "Click me", onClick = {}).GetComposable()
            ButtonUiModel.AppSimpleButton(text = "Click me", enabled = false, onClick = {})
                .GetComposable()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OutlinedButtonPreview() {
    UiKitTheme {
        Column {
            ButtonUiModel.AppOutlinedButton(text = "Click me", onClick = {}).GetComposable()
            ButtonUiModel.AppOutlinedButton(text = "Click me", enabled = false, onClick = {})
                .GetComposable()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextButtonPreview() {
    UiKitTheme {
        Column {
            ButtonUiModel.AppTextButton(text = "Click me", onClick = {}).GetComposable()
            ButtonUiModel.AppTextButton(text = "Click me", enabled = false, onClick = {})
                .GetComposable()
        }
    }
}