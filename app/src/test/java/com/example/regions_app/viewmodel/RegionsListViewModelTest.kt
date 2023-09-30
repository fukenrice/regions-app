package com.example.regions_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.regions_app.MainCoroutineScopeRule
import com.example.regions_app.data.api.ApiService
import com.example.regions_app.data.model.RegionModel
import com.example.regions_app.data.model.ResponseModel
import com.example.regions_app.data.utils.Likes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class RegionsListViewModelTest {
    private lateinit var viewModel: RegionsListViewModel
    private lateinit var apiService: ApiService
    private lateinit var likes: Likes
    private val regions = listOf<RegionModel>(
        RegionModel(
            title = "Москва",
            thumbUrls = listOf("1", "2"),
            viewsCount = 10
        ),
        RegionModel(
            title = "Санкт-Петербург",
            thumbUrls = listOf("1", "2"),
            viewsCount = 10
        )
    )
    private val response = ResponseModel(regions)

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineScope =  MainCoroutineScopeRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = runTest {
        apiService = Mockito.mock(ApiService::class.java)
        likes = Likes()
        viewModel = RegionsListViewModel(apiService, Dispatchers.Main, likes)
    }

    @Test
    fun getRegionsSuccessList() = runTest{
        `when`(apiService.getRegions()).thenReturn(response)
        viewModel.getRegions()
        val expectedList = listOf<RegionModel>(
            RegionModel(
                title = "Москва",
                thumbUrls = listOf("1", "2"),
                viewsCount = 10
            ),
            RegionModel(
                title = "Санкт-Петербург",
                thumbUrls = listOf("1", "2"),
                viewsCount = 10
            )
        )
        assertEquals(expectedList, viewModel.regionsList.value!!.data)

    }

    @Test
    fun getRegionsErrorList() = runTest{
        `when`(apiService.getRegions()).thenThrow(RuntimeException("exception"))
        viewModel.getRegions()
        val expectedError = "Ошибка получения данных, проверьте подключение к сети"
        assertEquals(expectedError, viewModel.regionsList.value!!.message)
    }

    @Test
    fun isEmpty() {
        assertTrue(viewModel.isEmpty())
    }

    @Test
    fun isNotEmpty() = runTest {
        `when`(apiService.getRegions()).thenReturn(response)
        viewModel.getRegions()
        assertFalse(viewModel.isEmpty())
    }
}