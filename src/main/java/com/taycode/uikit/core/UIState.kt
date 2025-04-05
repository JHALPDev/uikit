package com.taycode.uikit.core

data class UIState<out T>(val data: T, val screenStates: ScreenStates)

sealed class ScreenStates {
    data object Loading : ScreenStates()
    data object Error : ScreenStates()
    data object Success : ScreenStates()
}
