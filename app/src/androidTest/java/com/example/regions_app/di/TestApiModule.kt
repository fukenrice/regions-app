package com.example.regions_app.di

import android.util.Log
import com.example.regions_app.data.api.ApiService
import com.example.regions_app.data.model.RegionModel
import com.example.regions_app.data.model.ResponseModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito

@Module
class TestApiModule {
    @Provides
    fun provideApi(): ApiService = runBlocking {
        val apiService =
            Mockito.mock(ApiService::class.java)
//            mockk<ApiService>()

        val regions = listOf<RegionModel>(
            RegionModel(
                title = "Москва",
                thumbUrls = listOf("https://static-basket-02.wb.ru/vol22/travel/preview-ffwHk_JJ1oj_1024x435.jpg"),
                viewsCount = 10
            ),
            RegionModel(
                title = "Санкт-Петербург",
                thumbUrls = listOf("https://static-basket-02.wb.ru/vol22/travel/preview-cenOsIMOPVj_1024x435.jpg"),
                viewsCount = 10
            )
        )
//        coEvery { apiService.getRegions() } returns ResponseModel(regions)
        Mockito.`when`(apiService.getRegions()).thenReturn(ResponseModel(regions))
        Log.d("mytag", "provideApi: mock")
        return@runBlocking apiService
    }
}