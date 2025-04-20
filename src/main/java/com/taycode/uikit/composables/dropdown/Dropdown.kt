package com.taycode.uikit.composables.dropdown

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.taycode.uikit.R
import com.taycode.uikit.composables.buttons.ButtonUiModel
import com.taycode.uikit.core.CombinedPreviews
import com.taycode.uikit.theme.UiKitTextStyle
import com.taycode.uikit.theme.UiKitTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun setTrailingIcon(
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector?,
    isExpanded: Boolean,
): @Composable (() -> Unit)? = {

    val icon = trailingIcon
        ?: if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    Icon(
        modifier = modifier,
        imageVector = icon,
        contentDescription = trailingIcon?.name, // Icona decorativa
        tint = colorScheme.onSurface
    )
}

@ExperimentalMaterial3Api
@Composable
fun <T> MinimalDropdownMenu(
    modifier: Modifier = Modifier,
    text: String,
    @StringRes hint: Int,
    trailingIcon: ImageVector?,
    elements: List<T>,
    itemToString: (T) -> String, // Convierte T a String para la UI
    onItemSelected: (T) -> Unit, // Devuelve el elemento seleccionado
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
            trailingIcon = setTrailingIcon(
                trailingIcon = trailingIcon, isExpanded = expanded
            ),
            label = {
                Text(text = stringResource(hint), style = UiKitTextStyle.Label)
            },
            value = selectedText,
            onValueChange = {},
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurface,
                cursorColor = colorScheme.primary,

                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surfaceVariant,

                focusedIndicatorColor = colorScheme.primary,
                unfocusedIndicatorColor = colorScheme.outline,

                focusedTrailingIconColor = colorScheme.primary,
                unfocusedTrailingIconColor = colorScheme.onSurfaceVariant,

                focusedLabelColor = colorScheme.primary,
                unfocusedLabelColor = colorScheme.onSurfaceVariant,
            ),
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

@ExperimentalMaterial3Api
@Composable
fun <T> MinimalOutlinedDropdownMenu(
    modifier: Modifier = Modifier,
    text: String,
    @StringRes hint: Int,
    elements: List<T>,
    trailingIcon: ImageVector?,
    itemToString: (T) -> String, // Convierte T a String para la UI
    onItemSelected: (T) -> Unit, // Devuelve el elemento seleccionado
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(text) }
    val colorScheme = MaterialTheme.colorScheme

    ExposedDropdownMenuBox(
        modifier = modifier, expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            textStyle = UiKitTextStyle.Body,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = setTrailingIcon(trailingIcon = trailingIcon, isExpanded = expanded),
            label = {
                Text(text = stringResource(hint), style = UiKitTextStyle.Label)
            },
            value = selectedText,
            onValueChange = {},
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurface,
                cursorColor = colorScheme.primary,

                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surfaceVariant,

                focusedIndicatorColor = colorScheme.primary,
                unfocusedIndicatorColor = colorScheme.outline,

                focusedTrailingIconColor = colorScheme.primary,
                unfocusedTrailingIconColor = colorScheme.onSurfaceVariant,

                focusedLabelColor = colorScheme.primary,
                unfocusedLabelColor = colorScheme.onSurfaceVariant,
            ),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    modifier: Modifier = Modifier,
    initialText: String = "",
    @StringRes hint: Int,
    isError: Boolean,
    trailingIcon: ImageVector?,
    @StringRes errorMessage: Int?,
    onItemSelected: (String) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(initialText) }
    val datePickerState = rememberDatePickerState()

    val interactionSource = remember { MutableInteractionSource() }
    val colorScheme = MaterialTheme.colorScheme

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            isError = isError, value = selectedText, onValueChange = {}, label = {
                Text(
                    style = UiKitTextStyle.Label,
                    text = if (isError) errorMessage?.let { stringResource(it) }.orEmpty()
                    else stringResource(hint)
                )
            }, readOnly = true, interactionSource = interactionSource.also {
                LaunchedEffect(it) {
                    it.interactions.collect { interaction ->
                        if (interaction is PressInteraction.Release) {
                            showDatePicker = !showDatePicker
                        }
                    }
                }
            }, trailingIcon = setTrailingIcon(
                isExpanded = showDatePicker, trailingIcon = trailingIcon
            ), textStyle = UiKitTextStyle.Label, colors = OutlinedTextFieldDefaults.colors(
                // Texto
                focusedTextColor = colorScheme.onSurface,
                unfocusedTextColor = colorScheme.onSurface,
                disabledTextColor = colorScheme.onSurfaceVariant,
                errorTextColor = colorScheme.error,

                // Fondo (container)
                focusedContainerColor = colorScheme.surface,
                unfocusedContainerColor = colorScheme.surface,
                disabledContainerColor = colorScheme.surfaceVariant,
                errorContainerColor = colorScheme.surface,

                // Cursor
                cursorColor = colorScheme.primary,
                errorCursorColor = colorScheme.error,

                // Borde
                focusedBorderColor = colorScheme.primary,
                unfocusedBorderColor = colorScheme.outline,
                disabledBorderColor = colorScheme.outline,
                errorBorderColor = colorScheme.error,

                // Icono final
                focusedTrailingIconColor = colorScheme.primary,
                unfocusedTrailingIconColor = colorScheme.onSurfaceVariant,
                disabledTrailingIconColor = colorScheme.onSurfaceVariant,
                errorTrailingIconColor = colorScheme.error,

                // Label
                focusedLabelColor = colorScheme.primary,
                unfocusedLabelColor = colorScheme.onSurfaceVariant,
                disabledLabelColor = colorScheme.onSurfaceVariant,
                errorLabelColor = colorScheme.error,

                // Placeholder
                focusedPlaceholderColor = colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
                disabledPlaceholderColor = colorScheme.onSurfaceVariant,
                errorPlaceholderColor = colorScheme.error,

                // Texto de ayuda (si llegas a usarlo)
                focusedSupportingTextColor = colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = colorScheme.onSurfaceVariant,
                errorSupportingTextColor = colorScheme.error
            ), modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .semantics {
                    //  contentDescription = "Campo data: $selectedText"
                })

        if (showDatePicker) {
            Popup(
                onDismissRequest = { /* Solo Cancel o OK cierra */ }, alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(colorScheme.surface)
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        DatePicker(
                            title = {
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .semantics {
                                            // contentDescription = "Selezione data"
                                        }) {
                                    ButtonUiModel.AppOutlinedButton(
                                        text = stringResource(R.string.cancelar), onClick = {
                                            showDatePicker = false
                                        }).GetComposable(modifier.weight(1f))

                                    ButtonUiModel.AppOutlinedButton(
                                        text = stringResource(R.string.ok), onClick = {
                                            val millis = datePickerState.selectedDateMillis
                                            if (millis != null) {
                                                val formattedDate = convertMillisToDate(millis)
                                                selectedText = formattedDate
                                                onItemSelected(formattedDate)
                                            }
                                            showDatePicker = false
                                        }).GetComposable(modifier.weight(1f))
                                }
                            }, state = datePickerState, showModeToggle = false
                        )
                    }
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@CombinedPreviews
@Composable
fun AppDropDownOpenedPreview() {
    UiKitTheme {
        DropDownUiModel.Minimal(
            text = "",
            hint = R.string.select_an_option,
            elements = listOf("Option 1", "Option 2", "Option 3"),
            itemToString = { it },
            onItemSelected = {}).GetComposable()
    }
}

@CombinedPreviews
@Composable
fun AppDropDownClosedPreview() {
    UiKitTheme {
        DropDownUiModel.Minimal(
            text = "",
            hint = R.string.select_an_option,
            elements = listOf("Option 1", "Option 2", "Option 3"),
            itemToString = { it },
            onItemSelected = {}).GetComposable()
    }
}

@CombinedPreviews
@Composable
fun DatePickerDockedPreview() {
    UiKitTheme {
        DropDownUiModel.DatePicker(
            text = "", hint = R.string.select_an_option, onItemSelected = {}).GetComposable()
    }
}