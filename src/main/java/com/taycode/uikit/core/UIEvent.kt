package com.taycode.uikit.core

open class UIEvent()

sealed class CommonUIEvent : UIEvent() {
    data object NoAction : CommonUIEvent()
}