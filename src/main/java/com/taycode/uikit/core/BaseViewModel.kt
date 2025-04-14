package com.taycode.uikit.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<U : UIEvent, V> : ViewModel() {
    private val _event = MutableSharedFlow<UIEvent>(replay = 0)
    val event = _event.asSharedFlow().conflate()

    private val _state: MutableStateFlow<UIState<V>> = MutableStateFlow(initUiState())
    val state = _state.asStateFlow()

    abstract fun initUiState(): UIState<V>

    open fun handleEvent(event: UIEvent) {
        viewModelScope.launch {
            Log.d(BaseViewModel::class.java.simpleName, "handleEvent: $event")
            _event.emit(event)
        }
    }

    fun updateData(function: (V) -> V) {
        _state.update {
            it.copy(data = function.invoke(it.data), screenStates = ScreenStates.Success)
        }
    }

    fun emitEvent(event: UIEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    fun emitLoading() = _state.update { it.copy(screenStates = ScreenStates.Loading) }

    fun emitError() = _state.update { it.copy(screenStates = ScreenStates.Error) }

}

