package com.apx8.mongoose.presentation.view.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apx8.mongoose.domain.constants.AppCodes
import com.apx8.mongoose.domain.constants.Stadium
import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.weather.CommonState
import com.apx8.mongoose.preference.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val prefManager: PrefManager
): ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _event = Channel<MainEvent>(Channel.BUFFERED)
    val event = _event.receiveAsFlow()

    init {
        initializeCurrentStadium()
        observeCurrentStadium()
        checkFirstRun()
    }

    private fun initializeCurrentStadium() {
        _uiState.value = _uiState.value.copy(
            currentStadium = getMyStadium()
        )
    }

    fun observeCurrentStadium() {
        viewModelScope.launch {
            uiState
                .map { it.currentStadium }
                .filter { it != Stadium.NAN }
                .collect { stadium ->
                    requestWeather(stadium)
                }
        }
    }

    fun refresh() {
        val stadium = _uiState.value.currentStadium
        if (stadium == Stadium.NAN || _uiState.value.isRefreshing) return

        requestWeather(
            stadium = stadium,
            showRefreshing = true,
            blockIfRefreshing = true
        )
    }

    fun retry() {
        val stadium = _uiState.value.currentStadium
        if (stadium == Stadium.NAN) return

        requestWeather(
            stadium = stadium,
            showRefreshing = false,
            blockIfRefreshing = false
        )
    }

    fun selectStadium(code: String) {
        val stadium = Stadium.from(code)
        if (stadium == Stadium.NAN) return
        if (_uiState.value.currentStadium == stadium) return

        _uiState.value = _uiState.value.copy(
            currentStadium = stadium
        )

        viewModelScope.launch {
            prefManager.setString(AppCodes.Pref.MY_STADIUM, stadium.code)
        }
    }

    private fun requestWeather(
        stadium: Stadium = _uiState.value.currentStadium,
        showRefreshing: Boolean = false,
        blockIfRefreshing: Boolean = false
    ) {
        if (stadium == Stadium.NAN) return
        if (blockIfRefreshing && _uiState.value.isRefreshing) return

        viewModelScope.launch {
            if (showRefreshing) {
                _uiState.value = _uiState.value.copy(isRefreshing = true)
            }

            try {
                fetchWeather(stadium)
            } finally {
                if (showRefreshing) {
                    _uiState.value = _uiState.value.copy(isRefreshing = false)
                }
            }
        }
    }

    private suspend fun fetchWeather(stadium: Stadium) {
        weatherRepository.getAllWeatherInfo(
            lat = stadium.lat,
            lon = stadium.lon,
            stadiumCode = stadium.code
        )
        .map { resource -> CommonState.fromResource(resource) }
        .collect { state ->
            when (state) {
                is CommonState.Success -> {
                    _uiState.value = _uiState.value.copy(
                        currentWeatherState = CommonState.Success(state.data.currentWeatherInfo),
                        forecastWeatherState = CommonState.Success(state.data.forecastWeatherInfo)
                    )
                }

                is CommonState.Error -> {
                    _uiState.value = _uiState.value.copy(
                        currentWeatherState = CommonState.Error(state.message),
                        forecastWeatherState = CommonState.Error(state.message)
                    )
                }

                is CommonState.Loading -> {
                    _uiState.value = _uiState.value.copy(
                        currentWeatherState = CommonState.Loading(),
                        forecastWeatherState = CommonState.Loading()
                    )
                }
            }
        }
    }

    /**
     * GET : `내가 선호하는 경기장`
     * @desc 기본으로 보여줄 경기장의 코드를 가져옴
     * @desc 기본값 == `서울 잠실 야구장(SOJ)`
     */
    private fun getMyStadium(): Stadium {
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

    private fun checkFirstRun() {
        if (prefManager.getBoolean(AppCodes.Pref.IS_FIRST_RUN)) {
            viewModelScope.launch {
                _event.send(MainEvent.ShowAppInfoDialog)
            }
        }
    }
}