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

@OptIn(ExperimentalMaterial3Api::class)
abstract class BaseScreen<VM : BaseViewModel<UiModel>, UiModel> {
    @Composable
    abstract fun Content(
        padding: PaddingValues,
        uiModel: UiModel,
        onEvent: (UIEvent) -> Unit,
    )

    @Composable
    open fun TopBar() {
    }

    @Composable
    open fun BottomBar(uiModel: UiModel, onEvent: (UIEvent) -> Unit) {
    }

    @Composable
    fun RenderWithState(
        navController: NavController,
        viewModel: VM,
    ) {
        val uiEvent =
            viewModel.event.collectAsStateWithLifecycle(initialValue = CommonUIEvent.NoAction)
        val state = viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(uiEvent.value) {
            handleEvent(uiEvent.value, navController)
        }
        Render(uiEvent = viewModel::handleEvent, uiState = state.value)
    }


    @Composable
    fun Render(
        uiState: UIState<UiModel>,
        uiEvent: (UIEvent) -> Unit,
    ) {

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
                BottomBar(uiModel = uiState.data, uiEvent)
            }, modifier = Modifier
        ) {
            Content(padding = it, uiModel = uiState.data, uiEvent)
        }
    }

    open fun handleEvent(event: UIEvent, navController: NavController) {
    }
}