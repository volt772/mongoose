package com.apx8.mongoose.presentation.view.vms

import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.weather.CommonState

data class MainUiState(
    val currentStadium: Stadium = Stadium.NAN,
    val isRefreshing: Boolean = false,
    val currentWeatherState: CommonState<CurrentWeatherInfo> = CommonState.Loading(),
    val forecastWeatherState: CommonState<ForecastWeatherInfo> = CommonState.Loading()
)