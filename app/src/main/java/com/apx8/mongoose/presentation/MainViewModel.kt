package com.apx8.mongoose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.location.LocationTracker
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.preference.PrefManager
import com.apx8.mongoose.presentation.MongooseApp.Companion.apiKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val prefManager: PrefManager
): ViewModel() {

//    private val _viewState = MutableStateFlow<CurrentWeatherState>(CurrentWeatherState.Loading)
//    val feed = _viewState.asStateFlow()
    /* 현재 날씨 정보*/
    private val _currentWeather: MutableStateFlow<CommonState<CurrentWeatherInfo>> = MutableStateFlow(CommonState.Loading())
    val currentWeather: StateFlow<CommonState<CurrentWeatherInfo>> = _currentWeather
//    private val _category: MutableStateFlow<State<List<CmdCategory>>> = MutableStateFlow(State.loading())
//    val category: StateFlow<State<List<CmdCategory>>> = _category
/* 예보 날씨 정보*/
    private val _forecastWeather: MutableStateFlow<CommonState<ForecastWeatherInfo>> = MutableStateFlow(CommonState.Loading())
    val forecastWeather: StateFlow<CommonState<ForecastWeatherInfo>> = _forecastWeather

//    private val _currentStadium: MutableStateFlow<Stadium> = MutableStateFlow(Stadium.NAN)
//    val currentStadium: StateFlow<Stadium> = _currentStadium
    /* 현재 선택된 경기장 (중복 호출로 인해, SharedFlow로 Emit함)*/
    private val _currentStadium: MutableSharedFlow<Stadium> = MutableSharedFlow(replay = 0)
    val currentStadium: SharedFlow<Stadium> = _currentStadium

//    private val _stadium: MutableStateFlow<Stadium> = MutableStateFlow(Stadium.SOJ)
//    val stadium: StateFlow<Stadium> = _stadium

    /**
     * Data Fetch Async
     */
    suspend fun fetch(stadium: Stadium) =
        coroutineScope {
            val response = listOf(
                async { loadCurrentWeatherInfo(stadium) },
                async { loadForecastInfo(stadium) }
            )

            response.awaitAll()
        }

    /**
     * FETCH : 현재 경기장 날씨
     * @flag : `Current`
     */
    private fun loadCurrentWeatherInfo(stadium: Stadium) {
        viewModelScope.launch {
            weatherRepository.getCurrentWeatherInfo(
                lat = stadium.lat, lon = stadium.lon, appId = apiKey
            )
            .map { resource -> CommonState.fromResource(resource) }
            .collect { state -> _currentWeather.value = state }
        }
    }

    /**
     * FETCH : 경기장의 날씨 예보 (3일간)
     * @flag : `Forecast`
     */
    private fun loadForecastInfo(stadium: Stadium) {
        viewModelScope.launch {
            weatherRepository.getForecastWeatherInfo(
                lat = stadium.lat, lon = stadium.lon, appId = apiKey
            )
            .map { resource -> CommonState.fromResource(resource) }
            .collect { state -> _forecastWeather.value = state }
        }
    }

    /**
     * 현재 선택된 경기장
     * @desc 단순 SharedFlow로 데이터 처리하며, 별도의 데이터 저장은 없음
     */
    fun setCurrentStadium(code: String) {
        viewModelScope.launch {
            _currentStadium.emit(Stadium.from(code))
        }
    }

    /**
     * PUT : `내가 선호하는 경기장`
     * @desc BottomSheet 리스트에서 선택한 경기장 코드를 Pref에 저장
     */
    fun setMyStadium(code: String) {
        viewModelScope.launch {
            prefManager.setMyStadium(code)
        }
    }

    /**
     * GET : `내가 선호하는 경기장`
     * @desc 기본으로 보여줄 경기장의 코드를 가져옴
     * @desc 기본값 == `서울 잠실 야구장(SOJ)`
     */
    fun getMyStadium(): Stadium {
        val code = prefManager.getMyStadium()
        val savedStadium = Stadium.from(code)

        return if (savedStadium == Stadium.NAN) {
            Stadium.SOJ
        } else {
            savedStadium
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