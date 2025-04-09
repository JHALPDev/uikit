package com.taycode.uikit.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data object UiKitTextStyle {

    // Titulares grandes
    val TitleLarge
        @Composable get() = MaterialTheme.typography.headlineLarge

    // Subtítulo o sección
    val TitleMedium
        @Composable get() = MaterialTheme.typography.titleMedium

    val TitleMediumBold
        @Composable get() = TitleMedium.copy(fontWeight = FontWeight.Bold)

    // Texto normal (contenido, párrafos)
    val Body
        @Composable get() = MaterialTheme.typography.bodyMedium

    val BodyBold
        @Composable get() = Body.copy(fontWeight = FontWeight.Bold)

    // Texto pequeño como labels, botones
    val Label
        @Composable get() = MaterialTheme.typography.labelSmall

    val LabelBold
        @Composable get() = Label.copy(fontWeight = FontWeight.Bold)


    val Button
        @Composable get() = MaterialTheme.typography.labelLarge.copy(
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.25.sp // Según guía Material
        )
}