package com.example.regions_app.data.api

import com.example.regions_app.data.model.ResponseModel
import retrofit2.http.GET

interface ApiService {
    @GET("getBrands")
    suspend fun getRegions(): ResponseModel
}