package com.apx.apx108.presentation.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import com.apx8.mongoose.presentation.ui.theme.AxIconGray
import com.apx8.mongoose.presentation.ui.theme.AxWhite
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(
    isSearching: Boolean,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCloseClick: () -> Unit,
    onFilterClick: () -> Unit,
    onRouteManageClick: () -> Unit,
    onInfoClick: () -> Unit,
) {

    val focusRequester = remember { FocusRequester() }

    // 🔥 검색 시작 시 자동 포커스
    LaunchedEffect(isSearching) {
        if (isSearching) {
            delay(100) // UI 안정화를 위한 약간의 지연 (필요시)
            focusRequester.requestFocus()
        }
    }

    SmallTopAppBar(
        title = { },
        actions = {
            IconButton(onClick = onInfoClick) {
                Icon(Icons.Default.Info, contentDescription = "검색 닫기", tint = AxWhite)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MgDarkBlue
        )
    )
}