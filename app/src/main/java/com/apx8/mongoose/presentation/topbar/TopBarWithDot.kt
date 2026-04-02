package com.apx8.mongoose.presentation.topbar

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.apx8.mongoose.presentation.ui.theme.AxWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithDot(
    onClickMore: () -> Unit
) {

    SmallTopAppBar(
        title = { Text(text = "") },
        modifier = Modifier.height(40.dp),
//        actions = {
//            IconButton(onClick = onClickMore) {
//                Icon(Icons.Default.Close, contentDescription = "더보기", tint = Color.White)
//            }
//        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = AxWhite
        )
    )
}
