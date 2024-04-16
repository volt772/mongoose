package com.apx8.mongoose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apx8.mongoose.MongooseApp.Companion.apiKey
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.location.LocationTracker
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.weather.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

//    private val _viewState = MutableStateFlow<CurrentWeatherState>(CurrentWeatherState.Loading)
//    val feed = _viewState.asStateFlow()
    private val _currentWeather: MutableStateFlow<CommonState<CurrentWeatherInfo>> = MutableStateFlow(CommonState.Loading())
    val currentWeather: StateFlow<CommonState<CurrentWeatherInfo>> = _currentWeather
//    private val _category: MutableStateFlow<State<List<CmdCategory>>> = MutableStateFlow(State.loading())
//    val category: StateFlow<State<List<CmdCategory>>> = _category
    private val _forecastWeather: MutableStateFlow<CommonState<ForecastWeatherInfo>> = MutableStateFlow(CommonState.Loading())
    val forecastWeather: StateFlow<CommonState<ForecastWeatherInfo>> = _forecastWeather


    var state by mutableStateOf(WeatherState())
        private set

    fun loadForecastInfo() {
        viewModelScope.launch {
            weatherRepository.getForecastWeatherInfo(
                lat = 37.4132, lon = 127.0016, appId = apiKey
            )
            .map { resource -> CommonState.fromResource(resource) }
            .collect { state ->
                _forecastWeather.value = state
            }
        }
    }

    fun loadWeatherInfo() {
        viewModelScope.launch {
            weatherRepository.getCurrentWeatherInfo(
                lat = 37.4132, lon = 127.0016, appId = apiKey
            )
                .map { resource -> CommonState.fromResource(resource) }
                .collect { state ->
                    _currentWeather.value = state
//                try {
//                    _viewState.value = CurrentWeatherState.Success(result)
//                } catch (e: Exception) {
//                    _viewState.value = CurrentWeatherState.Error(e)
//                }
            }
        }

//        viewModelScope.launch {
//            state = state.copy(isLoading = true, error = null)
//
//            locationTracker.getCurrentLocation()?.let { location ->
//                when(val result = weatherRepository.getWeatherData(location.latitude, location.longitude)) {
//                    is Resource.Success -> {
//                        state = state.copy(
//                            weatherInfo = result.data,
//                            isLoading = false,
//                            error = null
//                        )
//                    }
//                    is Resource.Error -> {
//                        state = state.copy(
//                            weatherInfo = null,
//                            isLoading = false,
//                            error = result.message
//                        )
//                    }
//                }
//            }?: run {
//                state = state.copy(
//                    isLoading = false,
//                    error = "Unknown Error"
//                )
//            }
//        }
    }
}