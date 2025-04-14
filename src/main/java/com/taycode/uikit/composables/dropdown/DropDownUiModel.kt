package com.taycode.uikit.composables.dropdown

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.room.util.copy
import com.taycode.uikit.composables.dropdown.ErrorState.None

sealed class ErrorState {
    object None : ErrorState()
    data class Error(@StringRes val errorMessage: Int) : ErrorState()
}

@OptIn(ExperimentalMaterial3Api::class)
sealed class DropDownUiModel<T> {
    abstract val text: String

    @get:StringRes
    abstract val hint: Int
    abstract val errorState: ErrorState
    abstract val onItemSelected: (T) -> Unit

    abstract fun clearError(): DropDownUiModel<T>

    data class Minimal<T>(
        val id: String,
        override val text: String,
        override val hint: Int,
        val elements: List<T>,
        val itemToString: (T) -> String,
        override val onItemSelected: (T) -> Unit,
        override val errorState: ErrorState = None
    ) : DropDownUiModel<T>() {
        override fun clearError(): Minimal<T> {
            return this.copy(errorState = None)
        }
    }

    data class DatePicker(
        override val text: String,
        override val hint: Int,
        override val errorState: ErrorState = None,
        override val onItemSelected: (String) -> Unit,
    ) : DropDownUiModel<String>() {
        override fun clearError(): DatePicker {
            return this.copy(errorState = None)
        }
    }


    fun isEmpty() = text.isBlank()

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
                isError = errorState is ErrorState.Error,
                errorMessage = (errorState as? ErrorState.Error)?.errorMessage,
                onItemSelected = { onItemSelected(it) }
            )
        }
    }
}
