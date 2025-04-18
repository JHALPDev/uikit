package com.taycode.uikit.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified T : BaseViewModel<A>, A> UiKitScaffold(
    modifier: Modifier = Modifier,
    noinline topBar: @Composable () -> Unit = {},
    noinline content: @Composable (PaddingValues, A, (UIEvent) -> Unit) -> Unit,
    noinline bottomBar: @Composable (A, (UIEvent) -> Unit) -> Unit = { _, _ -> },
    noinline onEvent: ((UIEvent) -> Unit)? = null,
) {
    val viewModel: T = koinViewModel()
    val uiEvent by viewModel.event.collectAsStateWithLifecycle(initialValue = CommonUIEvent.NoAction)
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = uiEvent) {
        onEvent?.invoke(uiEvent)
        //No need to call onEvent with NoAction after every event
    }


    if (state.screenStates is ScreenStates.Loading) {
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
        content.invoke(it, state.data, viewModel::handleEvent)
    }, bottomBar = {
        Box(modifier = Modifier
            .navigationBarsPadding()
            .wrapContentSize()) {
            bottomBar.invoke(state.data, viewModel::handleEvent)
        }
    }, modifier = modifier)
}