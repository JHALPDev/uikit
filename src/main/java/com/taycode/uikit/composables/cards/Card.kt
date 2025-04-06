package com.taycode.uikit.composables.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.taycode.uikit.theme.UiKitTextStyle
import com.taycode.uikit.theme.UiKitTheme

@Composable
fun MinimalInfoCard(modifier: Modifier = Modifier, title: String, subtitle: String) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title, style = UiKitTextStyle.TitleMediumBold
            )
            Text(
                text = subtitle,
                style = UiKitTextStyle.Body,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun HorizontalInfoCard(modifier: Modifier = Modifier, title: String, subtitle: String) {
    Card(
        modifier = modifier.fillMaxWidth(), // Suggestion 1: Fill width
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), // Suggestion 3: Fill width of Column
            horizontalArrangement = Arrangement.SpaceAround, // Suggestion 4: Center Vertically
        ) {
            Text(
                textAlign = TextAlign.Start,
                text = title,
                modifier = modifier.weight(1f),
                style = UiKitTextStyle.TitleMediumBold
            )
            Spacer(modifier = Modifier.height(4.dp)) // Suggestion 6: Use Spacer for spacing
            Text(
                textAlign = TextAlign.End,
                modifier = modifier.weight(1f),
                text = subtitle,
                style = UiKitTextStyle.Body,
            )
        }
    }
}

@Composable
@Preview
fun HorizontalInfoCardPreview() {
    UiKitTheme {
        Column {
            HorizontalInfoCard(title = "Title", subtitle = "Subtitle")
            HorizontalInfoCard(
                title = "Title Title Title Title Title Title ",
                subtitle = "Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle "
            )
        }
    }
}

@Composable
@Preview
fun MinimalInfoCardPreview() {
    UiKitTheme {
        Column {
            MinimalInfoCard(title = "Title", subtitle = "Subtitle")
            MinimalInfoCard(
                title = "Title Title Title Title Title Title ",
                subtitle = "Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle Subtitle "
            )
        }
    }
}
