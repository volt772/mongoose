package com.apx8.mongoose.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apx8.mongoose.R
import com.apx8.mongoose.domain.dto.OpenSourceDto
import com.apx8.mongoose.domain.util.Resource
import com.apx8.mongoose.domain.weather.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OpenSourceViewModel @Inject constructor(
    private val context: Context,
): ViewModel() {
    private val _openSource: MutableStateFlow<CommonState<List<OpenSourceDto>>> = MutableStateFlow(CommonState.Loading())
    val openSource: StateFlow<CommonState<List<OpenSourceDto>>> = _openSource

    init {
        composeOpenSources()
    }

    private fun composeOpenSources() {
        viewModelScope.launch {
            val source = mutableListOf<OpenSourceDto>().also { _list ->
                context.resources.getStringArray(R.array.licenses).forEach { _os ->
                    _list.add(
                        OpenSourceDto(
                            name = _os,
                            description = getLicenseContent(_os),
                        )
                    )
                }
            }.toList()

            flowOf(Resource.Success(source))
                .map { resource -> CommonState.fromResource(resource) }
                .collect { state -> _openSource.value = state }
        }
    }

    private fun getLicenseContent(licenseName: String): String {
        val licenseRes = StringBuilder()
        try {
            val filePath = "licenses/$licenseName"
            context.assets.open(filePath).bufferedReader().use {
                val line = it.readText()
                licenseRes.append(line)
            }
        } catch (e: IOException) {
            return ""
        }

        return licenseRes.toString()
    }
}
