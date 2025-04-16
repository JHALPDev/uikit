package com.taycode.uikit.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
abstract class BaseScreen<T : BaseViewModel<out UIEvent, A>, A> {

    @Composable
    abstract fun Content(
        padding: PaddingValues,
        uiModel: A,
        onEvent: (UIEvent) -> Unit,
    )

    @Composable
    open fun TopBar() {
    }

    @Composable
    open fun BottomBar(uiModel: A, onEvent: (UIEvent) -> Unit) {
    }

    @Composable
    fun RenderWithState(
        navController: NavController = rememberNavController(),
        viewModel: BaseViewModel<out UIEvent, A> = koinViewModel<BaseViewModel<out UIEvent, A>>(),
    ) {
        val uiEvent =
            viewModel.event.collectAsStateWithLifecycle(initialValue = CommonUIEvent.NoAction)
        val state = viewModel.state.collectAsStateWithLifecycle()
        Render(navController, uiEvent.value, state.value, viewModel::handleEvent)
    }

    @Composable
    fun Render(
        navController: NavController = rememberNavController(),
        uiEvent: UIEvent,
        uiState: UIState<A>,
        handleEvent: (UIEvent) -> Unit,
    ) {

        LaunchedEffect(uiEvent) {
            handleEvent(uiEvent)
        }

        if (uiState.screenStates is ScreenStates.Loading) {
            Dialog(
                onDismissRequest = {}, properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    dismissOnBackPress = false,
                    dismissOnClickOutside = false,
                    decorFitsSystemWindows = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x88000000)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(strokeWidth = 8.dp, color = Color.Blue)
                }
            }
        }

        Scaffold(
            topBar = { TopBar() }, bottomBar = {
            BottomBar(uiModel = uiState.data, ::handleEvent)
        }, modifier = Modifier
        ) {
            Content(padding = it, uiModel = uiState.data, ::handleEvent)
        }
    }

    abstract fun handleEvent(event: UIEvent)
}