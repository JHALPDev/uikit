package com.taycode.uikit.composables.dropdown

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.taycode.uikit.theme.UiKitTextStyle
import com.taycode.uikit.theme.UiKitTheme

@ExperimentalMaterial3Api
@Composable
fun <T> MinimalDropdownMenu(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    elements: List<T>,
    itemToString: (T) -> String, // Convierte T a String para la UI
    onItemSelected: (T) -> Unit // Devuelve el elemento seleccionado
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }

    ExposedDropdownMenuBox(
        modifier = modifier, expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            textStyle = UiKitTextStyle.Body,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            label = {
                Text(text = hint, style = UiKitTextStyle.Label)
            },
            value = selectedText,
            onValueChange = {},
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            elements.forEach { item ->
                DropdownMenuItem(
                    text = { Text(itemToString(item)) }, // Convierte T a String
                    onClick = {
                        selectedText = itemToString(item)
                        onItemSelected(item) // Devuelve el elemento seleccionado
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDropDownOpenedPreview() {
    UiKitTheme {
        DropDownUiModel.Minimal(
            id = "",
            text = "",
            hint = "Select an option",
            elements = listOf("Option 1", "Option 2", "Option 3")
        ).GetComposable(itemToString = { it }, onItemSelected = {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDropDownClosedPreview() {
    UiKitTheme {
        DropDownUiModel.Minimal(
            id = "",
            text = "",
            hint = "Select an option",
            elements = listOf("Option 1", "Option 2", "Option 3")
        ).GetComposable(itemToString = { it }, onItemSelected = {})
    }
}