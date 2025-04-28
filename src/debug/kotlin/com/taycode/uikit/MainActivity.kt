package com.taycode.uikit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.taycode.uikit.composables.cards.MinimalInfoCard
import com.taycode.uikit.theme.UiKitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UiKitTheme {
                MinimalInfoCard(
                    modifier = Modifier.fillMaxSize(),
                    title = "Title",
                    subtitle = "Subtitle"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MinimalInfoCardPreview() {
    UiKitTheme {
        MinimalInfoCard(
            modifier = Modifier.fillMaxSize(),
            title = "Title",
            subtitle = "Subtitle"
        )
    }
}