package com.taycode.uikit.core

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.taycode.uikit.R

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
        if (uiState.screenStates is ScreenStates.Error) {
            ErrorDialog(
                message = "Ocurrió un error inesperado",
                onDismissRequest = {}
            )
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


    @Composable
    fun ErrorDialog(
        message: String,
        onDismissRequest: () -> Unit,
    ) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                decorFitsSystemWindows = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)), // Use copy for alpha
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // Adjust width as needed
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.error_title), // Use string resource
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Example: Add a button to dismiss the dialog if needed
                    /*
                    Button(onClick = onDismissRequest, modifier = Modifier.padding(top = 16.dp)) {
                        Text(stringResource(R.string.ok))
                    }
                    */
                }
            }
        }
    }
}

