package com.taycode.uikit.core

import android.R.attr.contentDescription
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.taycode.uikit.theme.UiKitTheme
import org.koin.compose.koinInject

@Composable
inline fun <reified T : BaseViewModel<out UIEvent, A>, A> UiKitScaffold(
    modifier: Modifier = Modifier,
    noinline topBar: @Composable () -> Unit = {},
    noinline bottomBar: @Composable () -> Unit = {},
//    noinline onEvent: ((UIEvent) -> Unit)? = null,
    noinline content: @Composable (PaddingValues, A, (UIEvent) -> Unit) -> Unit,
) {
    val viewModel = koinInject<T>()
    val event = viewModel.event.collectAsStateWithLifecycle()
    val state = viewModel.state.collectAsStateWithLifecycle()


    if (state.value.screenStates is ScreenStates.Loading) {
        Dialog(
            onDismissRequest = {}, properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                decorFitsSystemWindows = false //Ensure it covers the entire screen
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    strokeWidth = 8.dp, color = Color.Blue
                )
            }
        }
    }

    Scaffold(topBar = topBar, content = {
        content.invoke(it, state.value.data, viewModel::handleEvent)
    }, bottomBar = bottomBar, modifier = modifier)
}