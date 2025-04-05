package com.taycode.uikit.composables.cards

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.taycode.uikit.theme.UiKitTheme

sealed class InfoCardUiModel{
    data class Minimal(val title: String, val subtitle: String): InfoCardUiModel()
    data class Horizontal(val title: String, val subtitle: String): InfoCardUiModel()


    @Composable
    fun GetComposable(){
        when(this){
            is Minimal -> MinimalInfoCard(title = title, subtitle = subtitle)
            is Horizontal -> HorizontalInfoCard(title = title, subtitle = subtitle)
        }
    }
}


@Composable
@Preview
fun InfoCardUiModelPreview(){
    UiKitTheme {
        Column {
            InfoCardUiModel.Minimal(title = "Title", subtitle = "Subtitle").GetComposable()
            InfoCardUiModel.Horizontal(title = "Title", subtitle = "Subtitle").GetComposable()
        }
    }
}