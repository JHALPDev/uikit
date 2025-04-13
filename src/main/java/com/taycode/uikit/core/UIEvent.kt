package com.taycode.uikit.core

import java.util.UUID

open class UIEvent() {
    open val uid: String = UUID.randomUUID().toString()
}

sealed class CommonUIEvent : UIEvent() {
    data object NoAction : CommonUIEvent()
}