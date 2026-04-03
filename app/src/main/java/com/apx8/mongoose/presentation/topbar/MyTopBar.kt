package com.apx8.mongoose.presentation.topbar

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.presentation.ui.theme.AxWhite
import com.apx8.mongoose.presentation.ui.theme.MgDarkBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    onInfoClick: () -> Unit
) {

    TopAppBar(
        title = { Text(text = "") },
        modifier = Modifier.height(40.dp),
        actions = {
            IconButton(onClick = onInfoClick) {
                Icon(Icons.Default.Info, contentDescription = "더보기", tint = AxWhite)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MgDarkBlue
        )
    )
}
