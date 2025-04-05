package com.taycode.uikit.composables.dropdown

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
sealed class DropDownUiModel<T> {
    abstract val id: String
    abstract val text: String
    abstract val hint: String
    abstract val elements: List<T>

    data class Minimal<T>(
        override val id: String,
        override val text: String,
        override val hint: String,
        override val elements: List<T>
    ) : DropDownUiModel<T>()

    @Composable
    fun GetComposable(itemToString: (T) -> String, onItemSelected: (T) -> Unit) {
        when (this) {
            is Minimal -> MinimalDropdownMenu(
                text = text,
                hint = hint,
                elements = elements,
                itemToString = itemToString,
                onItemSelected = onItemSelected
            )
        }
    }
}
