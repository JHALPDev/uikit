package com.taycode.uikit.core

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
abstract class BaseScreen<VM : BaseViewModel<UiModel>, UiModel> {

    @Composable
    abstract fun Content(
        padding: PaddingValues,
        uiModel: UiModel,
        onEvent: (UIEvent) -> Unit,
    )

    @Composable
    open fun TopBar(uiEvent: (UIEvent) -> Unit) {
    }

    @Composable
    open fun BottomBar(uiModel: UiModel, onEvent: (UIEvent) -> Unit) {
    }

    @Composable
    fun RenderScreen(
        navController: NavController = rememberNavController(),
        viewModel: VM,
    ) {
        val uiEvent by viewModel.event.collectAsStateWithLifecycle(initialValue = CommonUIEvent.NoAction)
        val state by viewModel.state.collectAsStateWithLifecycle()

        handleEvent(uiEvent, navController)


        RenderScreenContent(
            uiEvent = {
                viewModel.handleEvent(it)
            }, uiState = state
        )
    }

    open fun handleEvent(event: UIEvent, navController: NavController) {
        Log.d(BaseScreen::class.java.simpleName, "handleEvent: $event")
    }

    @Composable
    fun RenderScreenContent(
        uiState: UIState<UiModel>,
        uiEvent: (UIEvent) -> Unit,
    ) {

        if (uiState.screenStates is ScreenStates.Loading) {
            LoadingIndicator()
        }

        Scaffold(
            topBar = { TopBar(uiEvent) }, bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .wrapContentSize()
                ) {
                    BottomBar(uiModel = uiState.data, uiEvent)
                }
            }, modifier = Modifier
        ) {
            Content(padding = it, uiModel = uiState.data, uiEvent)
        }
    }

    @Composable
    private fun LoadingIndicator() {
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
}