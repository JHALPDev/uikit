package com.taycode.uikit.core

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<U : UIEvent, V> : ViewModel() {
    private val _event: MutableStateFlow<UIEvent> = MutableStateFlow(CommonUIEvent.NoAction)
    val event = _event.asStateFlow()

    private val _state: MutableStateFlow<UIState<V>> = MutableStateFlow(initUiState())
    val state = _state.asStateFlow()

    abstract fun initUiState(): UIState<V>

    open fun handleEvent(event: UIEvent) {
        Log.d(BaseViewModel::class.java.simpleName, "handleEvent: ")
    }

    fun updateData(function: (V) -> V) {
        _state.update {
            it.copy(data = function.invoke(it.data), screenStates = ScreenStates.Success)
        }
    }

    fun emitLoading() = _state.update { it.copy(screenStates = ScreenStates.Loading) }

    fun emitError() = _state.update { it.copy(screenStates = ScreenStates.Error) }

}

