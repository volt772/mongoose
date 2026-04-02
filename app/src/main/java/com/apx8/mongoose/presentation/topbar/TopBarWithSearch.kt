package com.apx.apx108.presentation.topbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import com.apx8.mongoose.presentation.ui.theme.AxWhite
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(
    onInfoClick: () -> Unit,
) {

    val focusRequester = remember { FocusRequester() }

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