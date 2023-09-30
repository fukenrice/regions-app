package com.example.regions_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.regions_app.data.model.RegionModel
import com.example.regions_app.data.utils.Likes
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestRule

class RegionDetailsViewModelTest {

    private lateinit var viewModel: RegionDetailsViewModel
    private lateinit var likes: Likes

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() = runTest {
        likes = Likes()

        viewModel = RegionDetailsViewModel(likes)
        viewModel.region = RegionModel(
            title = "Москва",
            thumbUrls = listOf("1", "2"),
            viewsCount = 10
        )
        likes.initializeRegions(listOf(viewModel.region!!))
    }

    @Test
    fun initLikeFalseTest() {
        viewModel.initLike()
        assertFalse(viewModel.liked.value!!)
    }

    @Test
    fun initLikeTrueTest() {
        likes.changeLike("Москва")
        viewModel.initLike()
        assertTrue(viewModel.liked.value!!)
    }

    @Test
    fun setLiked() {
        viewModel.initLike()
        viewModel.setLiked()
        assertTrue(likes.getRegion("Москва"))
    }
}