package com.taycode.uikit.composables.topbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.taycode.uikit.theme.UiKitTextStyle

sealed class TopbarUiModel {
    data class Centered(val title: String) : TopbarUiModel()


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GetComposable(modifier: Modifier = Modifier) = when (this) {
        is Centered -> CenteredTopBar(modifier = modifier.fillMaxWidth(), title = title)
    }
}

@ExperimentalMaterial3Api
@Composable
fun CenteredTopBar(
    modifier: Modifier = Modifier,
    title: String,
    //onBack: () -> Unit, // Add a lambda to handle back button clicks
) {
    CenterAlignedTopAppBar(
        modifier = modifier, // Apply modifier to the top bar itself
        navigationIcon = {
            IconButton(onClick = {}) { // Use the provided lambda
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        title = {
            Text(
                text = title,
                style = UiKitTextStyle.TitleLarge
            )
        }
    )
}

@Composable
@Preview
fun CenteredTopBarPreview() {
    TopbarUiModel.Centered(title = "Centered Top Bar").GetComposable()
}