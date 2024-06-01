package com.apx8.mongoose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.presentation.MongooseApp.Companion.apiKey
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.location.LocationTracker
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.weather.CommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
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

    private val _currentStadium: MutableStateFlow<Stadium> = MutableStateFlow(Stadium.NAN)
    val currentStadium: StateFlow<Stadium> = _currentStadium

    private val _stadium: MutableStateFlow<Stadium> = MutableStateFlow(Stadium.SOJ)
    val stadium: StateFlow<Stadium> = _stadium

    suspend fun fetch(stadium: Stadium) =
        coroutineScope {
            val response = listOf(
                async { loadCurrentWeatherInfo(stadium) },
                async { loadForecastInfo(stadium) }
            )

            response.awaitAll()
        }

//    fun fetch(stadium: Stadium) {
//        viewModelScope.launch {
//            loadCurrentWeatherInfo(stadium)
//            loadForecastInfo(stadium)
//        }
//    }

    private fun loadCurrentWeatherInfo(stadium: Stadium) {
        viewModelScope.launch {
            weatherRepository.getCurrentWeatherInfo(
                lat = stadium.lat, lon = stadium.lon, appId = apiKey
            )
            .map { resource -> CommonState.fromResource(resource) }
            .collect { state -> _currentWeather.value = state }
        }
    }

    private fun loadForecastInfo(stadium: Stadium) {
        viewModelScope.launch {
            weatherRepository.getForecastWeatherInfo(
                lat = stadium.lat, lon = stadium.lon, appId = apiKey
            )
            .map { resource -> CommonState.fromResource(resource) }
            .collect { state -> _forecastWeather.value = state }
        }
    }

    fun setCurrentStadium(code: String) {
        viewModelScope.launch {
            _currentStadium.emit(Stadium.from(code))
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