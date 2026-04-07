package com.apx8.mongoose.presentation.view.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apx8.mongoose.presentation.view.display.MainScreen
import com.apx8.mongoose.presentation.view.vms.MainEvent
import com.apx8.mongoose.presentation.view.vms.MainViewModel

@Composable
fun MainRoute(
    vm: MainViewModel,
    onInfoClick: () -> Unit,
) {

    val uiState = vm.uiState.collectAsStateWithLifecycle().value
    var showAppInfoDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.event.collect { event ->
            when (event) {
                MainEvent.ShowAppInfoDialog -> {
                    showAppInfoDialog = true
                }
            }
        }
    }

    MainScreen(
        uiState = uiState,
        showAppInfoDialog = showAppInfoDialog,
        onConfirmAppInfo = {
            showAppInfoDialog = false
            vm.setIsFirstRun()
        },
        onInfoClick = onInfoClick,
        onRefresh = {
            vm.refresh()
        },
        onRetry = {
            vm.retry()
        },
        onSelectStadium = { code ->
            vm.selectStadium(code)
        }
    )