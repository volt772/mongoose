package com.apx8.mongoose.di

import com.apx8.mongoose.domain.repository.WeatherRepository
import com.apx8.mongoose.domain.usecase.GetAllWeatherInfo
import com.apx8.mongoose.domain.usecase.GetCurrentWeatherInfo
import com.apx8.mongoose.domain.usecase.GetForecastWeatherInfo
import com.apx8.mongoose.domain.usecase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun provideWeatherUseCase(
        repository: WeatherRepository
    ): WeatherUseCase {
        return WeatherUseCase(
            getAllWeatherInfo = GetAllWeatherInfo(repository),
            getCurrentWeatherInfo = GetCurrentWeatherInfo(repository),
            getForecastWeatherInfo = GetForecastWeatherInfo(repository)
        )
    }
}