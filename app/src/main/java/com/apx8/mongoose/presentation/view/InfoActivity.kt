package com.apx8.mongoose.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoActivity: ComponentActivity() {

    private val vm: InfoViewModel by viewModels()


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            InfoScreen(::infoBackPressed)
        }
    }

    private fun infoBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }
}

@ExperimentalMaterial3Api
@Composable
fun InfoScreen(backPressed: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MgWhite,
                    ),
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = {
                            backPressed.invoke()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "뒤로가기"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MgWhite)
                    .padding(innerPadding)
            ) {
                
                Text(text = "hamster!!")
                
            }
        }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewInfoScreen() {
    InfoScreen(
        backPressed = {}
    )
}
