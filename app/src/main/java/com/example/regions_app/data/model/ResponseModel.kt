package com.example.regions_app.data.model

import com.google.gson.annotations.SerializedName

data class ResponseModel (
    @SerializedName("brands")
    val regions: List<RegionModel>
)