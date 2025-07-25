package com.apx8.mongoose.presentation.view.vms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apx8.mongoose.domain.constants.AppCodes
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.dto.CurrentWeatherInfo
import com.apx8.mongoose.domain.dto.ForecastWeatherInfo
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.preference.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val prefManager: PrefManager
): ViewModel() {

    /**
     * Loading Status
     * @desc 현재날씨 (CurrentWeather) 데이터 로드를 기준으로 로딩이 완료됨을 판단함
     */
    var onLoading by mutableStateOf(true)

    /**
     * Failed Status
     * @desc 현재날씨 (CurrentWeather) 데이터 로드를 기준으로 로딩이 실패됨을 판단함
     */
    var isFailed by mutableStateOf(false)

    /* 현재 날씨 정보*/
    private val _currentWeather: MutableStateFlow<CommonState<CurrentWeatherInfo>> = MutableStateFlow(CommonState.Loading())
    val currentWeather: StateFlow<CommonState<CurrentWeatherInfo>> = _currentWeather

    /* 예보 날씨 정보*/
    private val _forecastWeather: MutableStateFlow<CommonState<ForecastWeatherInfo>> = MutableStateFlow(CommonState.Loading())
    val forecastWeather: StateFlow<CommonState<ForecastWeatherInfo>> = _forecastWeather

    /**
     * 현재 선택된 경기장
     * @data Stadium
     * @desc Flow 중복 호출로 인해, SharedFlow로 Emit함)
     */
    private val _currentStadium: MutableSharedFlow<Stadium> = MutableSharedFlow(replay = 0)
    val currentStadium: SharedFlow<Stadium> = _currentStadium

    /* 앱초기실행여부*/
    /**
     * 앱초기실행여부
     * @desc 앱 초기 실행시, 데이터 관련 안내 팝업을 보여줄 용도로만 사용됨
     */
    private val _isFirstRun: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isFirstRun: StateFlow<Boolean> = _isFirstRun

    init {
        getIsFirstRun()
    }

    /**
     * Data Fetch Async
     * @fetch1 현재날씨
     * @fetch2 예보날씨
     */
    fun fetch(stadium: Stadium) {
        loadWeatherInfo(stadium)
    }

    private fun loadWeatherInfo(stadium: Stadium) {
        viewModelScope.launch {
            weatherRepository.getAllWeatherInfo(
                lat = stadium.lat, lon = stadium.lon, stadiumCode = stadium.code
            )
            .map { resource -> CommonState.fromResource(resource) }
            .collect { state ->
                when(state) {
                    is CommonState.Success -> {
                        _currentWeather.value = CommonState.Success(state.data.currentWeatherInfo)
                        _forecastWeather.value = CommonState.Success(state.data.forecastWeatherInfo)
                    }
                    is CommonState.Error -> {
                        _currentWeather.value = CommonState.Error(state.message)
                        _forecastWeather.value = CommonState.Error(state.message)
                    }
                    is CommonState.Loading -> {
                        _currentWeather.value = CommonState.loading()
                        _forecastWeather.value = CommonState.loading()
                    }
                }
            }
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
            prefManager.setString(AppCodes.Pref.MY_STADIUM, code)
        }
    }

    /**
     * GET : `내가 선호하는 경기장`
     * @desc 기본으로 보여줄 경기장의 코드를 가져옴
     * @desc 기본값 == `서울 잠실 야구장(SOJ)`
     */
    fun getMyStadium(): Stadium {
        val code = prefManager.getString(AppCodes.Pref.MY_STADIUM, Stadium.NAN.code)
        val savedStadium = Stadium.from(code)

        return if (savedStadium == Stadium.NAN) {
            Stadium.SOJ
        } else {
            savedStadium
        }
    }

    /**
     * PUT : `앱 첫실행 여부`
     * @desc 첫 AlertDialog에서 `확인하였습니다` 버튼 선택 시 Pref에 저장
     */
    fun setIsFirstRun() {
        viewModelScope.launch {
            prefManager.setBoolean(AppCodes.Pref.IS_FIRST_RUN, false)
        }
    }

    /**
     * GET : `앱 첫실행 여부`
     * @desc 첫 실행여부를 저장
     */
    private fun getIsFirstRun() {
        _isFirstRun.value = prefManager.getBoolean(AppCodes.Pref.IS_FIRST_RUN)
    }
}