package com.apx8.mongoose.presentation.view.vms

sealed interface MainEvent {
    data object ShowAppInfoDialog : MainEvent
}