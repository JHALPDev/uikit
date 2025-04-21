package com.taycode.uikit.composables.dropdown

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.taycode.uikit.R
import com.taycode.uikit.composables.dropdown.ErrorState.None
import com.taycode.uikit.core.CombinedPreviews
import com.taycode.uikit.theme.UiKitTheme

sealed class ErrorState {
    object None : ErrorState()
    data class Error(@StringRes val errorMessage: Int) : ErrorState()
}

@OptIn(ExperimentalMaterial3Api::class)
sealed class DropDownUiModel<T> {
    abstract val text: String

    @get:StringRes
    abstract val hint: Int
    abstract val trailingIcon: ImageVector?
    abstract val errorState: ErrorState
    abstract val onItemSelected: (T) -> Unit

    abstract fun clearError(): DropDownUiModel<T>

    data class Minimal<T>(
        override val text: String,
        override val hint: Int,
        override val errorState: ErrorState = None,
        override val trailingIcon: ImageVector? = null,
        val elements: List<T>,
        val itemToString: (T) -> String,
        override val onItemSelected: (T) -> Unit,
    ) : DropDownUiModel<T>() {
        override fun clearError(): Minimal<T> {
            return this.copy(errorState = None)
        }
    }

    data class MinimalOutLined<T>(
        override val text: String,
        override val hint: Int,
        val elements: List<T>,
        override val errorState: ErrorState = None,
        override val trailingIcon: ImageVector? = null,
        val itemToString: (T) -> String,
        val itemToIcon: ((T) -> ImageVector?)? = null,
        val itemToColor: ((T) -> androidx.compose.ui.graphics.Color?)? = null,
        override val onItemSelected: (T) -> Unit,
    ) : DropDownUiModel<T>() {
        override fun clearError(): MinimalOutLined<T> {
            return this.copy(errorState = None)
        }
    }

    data class DatePicker(
        override val text: String,
        override val hint: Int,
        override val trailingIcon: ImageVector = Icons.Default.DateRange,
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
                trailingIcon = trailingIcon,
                onItemSelected = onItemSelected
            )

            is MinimalOutLined -> MinimalOutlinedDropdownMenu(
                text = text,
                hint = hint,
                elements = elements,
                trailingIcon = trailingIcon,
                itemToString = itemToString,
                itemToIcon = itemToIcon,
                onItemSelected = onItemSelected,
                itemToColor = itemToColor
            )

            is DatePicker -> DatePickerDocked(
                initialText = text,
                hint = hint,
                isError = errorState is ErrorState.Error,
                trailingIcon = trailingIcon,
                errorMessage = (errorState as? ErrorState.Error)?.errorMessage,
                onItemSelected = { onItemSelected(it) })
        }
    }
}

@Composable
@CombinedPreviews
fun DropDownUiModelMinimalPreview() {
    UiKitTheme {
        DropDownUiModel.Minimal(
            text = "Selecciona una opción",
            hint = R.string.select_an_option,
            elements = listOf("Opción 1", "Opción 2", "Opción 3"),
            itemToString = { it },
            onItemSelected = {}).GetComposable()
    }

}

@Composable
@CombinedPreviews
fun DropDownUiModelMinimalOutlinedPreview() {
    UiKitTheme {
        DropDownUiModel.MinimalOutLined(
            text = "Selecciona una opción",
            hint = R.string.select_an_option,
            elements = listOf("Opción 1", "Opción 2", "Opción 3"),
            itemToString = { it },
            onItemSelected = {}).GetComposable()
    }
}

@Composable
@CombinedPreviews
fun DropDownUiModelDatePickerOutlinedPreview() {
    UiKitTheme {
        DropDownUiModel.DatePicker(
            text = "Selecciona una fecha", hint = R.string.select_an_option, onItemSelected = {})
            .GetComposable()
    }

}



