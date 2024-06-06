package com.apx8.mongoose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apx8.mongoose.domain.dto.OpenSourceDto
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.presentation.ext.BackButtonScaffoldScreen
import com.apx8.mongoose.presentation.ext.SetStatusBarColor
import com.apx8.mongoose.presentation.ui.theme.MgBackgroundGray
import com.apx8.mongoose.presentation.ui.theme.MgBlue
import com.apx8.mongoose.presentation.ui.theme.MgLicenseFontGray
import com.apx8.mongoose.presentation.ui.theme.MgWhite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OpenSourceActivity: ComponentActivity() {

    private val vm: OpenSourceViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SetStatusBarColor()
            LicenseScreen(::infoBackPressed)
        }
    }

    private fun infoBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    @ExperimentalMaterial3Api
    @Composable
    fun LicenseScreen(backPressed: () -> Unit) {
        BackButtonScaffoldScreen(
            title = "오픈라이센스",
            content = { LicenseDisplay() },
            backPressed = backPressed
        )
    }

    @Composable
    fun LicenseDisplay() {
        when (val state = vm.openSource.collectAsStateWithLifecycle().value) {
            is CommonState.Loading -> { }
            is CommonState.Error -> { }
            is CommonState.Success -> {
                LicenseList(state.data)
            }
        }
    }

    @Composable
    fun LicenseList(licenses: List<OpenSourceDto>) {
        LazyColumn {
            items(licenses) { license ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MgWhite)
                        .padding(16.dp),

                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    /* 라이센스 이름*/
                    Text(
                        text = license.name,
                        fontSize = 16.sp,
                        color = MgLicenseFontGray,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    /* 구분자*/
                    HorizontalDivider(
                        modifier = Modifier
                            .width(30.dp)
                            .height(4.dp)
                            .background(MgBlue)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    /* 라이센스 설명*/
                    Text(
                        modifier = Modifier.align(Alignment.Start),
                        text = license.description,
                        fontSize = 14.sp,
                        color = MgLicenseFontGray,
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.height(1.dp).background(MgBackgroundGray)
                )
            }
        }

    }

    @Preview
    @Composable
    fun PreviewLicenseList() {
        val licenses = mutableListOf<OpenSourceDto>().also {
            it.add(OpenSourceDto(name="lic1", description = "Lorem Ipsum is simply dummy text of the printing "))
            it.add(OpenSourceDto(name="lic2", description = "Lorem Ipsum is simp\n Lorem Ipsum is simp\n Lorem Ipsum is simp\n Lorem Ipsum is simp\n  Lorem Ipsum is simp\n Lorem Ipsum is simp\n   "))
            it.add(OpenSourceDto(name="lic3", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."))
            it.add(OpenSourceDto(name="lic4", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."))
            it.add(OpenSourceDto(name="lic5", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."))
            it.add(OpenSourceDto(name="lic6", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."))
            it.add(OpenSourceDto(name="lic7", description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."))
        }

        LicenseList(
            licenses = licenses
        )
    }

    @Preview
    @Composable
    fun PreviewLicenseScreen() {
        LicenseDisplay(
        )
    }
}
