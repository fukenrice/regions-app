package com.example.regions_app.data.api

import com.example.regions_app.data.model.RegionModel
import retrofit2.http.GET

interface ApiService {
    @GET("getBrands")
    suspend fun getRegions(): RegionModel
}