package com.taycode.uikit.core

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Custom annotation that combines different preview configurations for Compose UI.
 *
 * This annotation can be used to quickly generate multiple previews with different
 * configurations (e.g., light/dark theme, different screen sizes) in Android Studio.
 */
@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Large Font", fontScale = 1.5f, showBackground = true)
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp,dpi=240", showBackground = true)
annotation class CombinedPreviews