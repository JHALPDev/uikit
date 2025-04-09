package com.taycode.uikit.composables.dropdown

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
sealed class DropDownUiModel<T> {
    abstract val text: String
    abstract val hint: String
    abstract val onItemSelected: (T) -> Unit

    data class Minimal<T>(
        val id: String,
        override val text: String,
        override val hint: String,
        val elements: List<T>,
        val itemToString: (T) -> String,
        override val onItemSelected: (T) -> Unit
    ) : DropDownUiModel<T>()

    data class DatePicker(
        override val text: String,
        override val hint: String,
        override val onItemSelected: (String) -> Unit,
    ) : DropDownUiModel<String>()

    @Composable
    fun GetComposable() {
        when (this) {
            is Minimal -> MinimalDropdownMenu(
                text = text,
                hint = hint,
                elements = elements,
                itemToString = itemToString,
                onItemSelected = onItemSelected
            )

            is DatePicker -> DatePickerDocked(
                initialText = text,
                hint = hint,
                onItemSelected = { onItemSelected(it) }
            )
        }
    }
}
