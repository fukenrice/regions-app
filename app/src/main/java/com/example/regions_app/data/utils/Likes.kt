package com.example.regions_app.data.utils

import com.example.regions_app.data.model.RegionModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Likes @Inject constructor(){
    private val likes: MutableMap<String, Boolean> = mutableMapOf()

    fun changeLike(regionName: String) {
        if (likes.containsKey(regionName)) {
            likes[regionName] = !likes[regionName]!!
        }
    }

    fun initializeRegions(regions: List<RegionModel>) {
        for (region in regions) {
            if (!likes.containsKey(region.title)) {
                likes[region.title] = false
            }
        }
    }

    fun getRegion(regionName: String): Boolean {
        if (likes.containsKey(regionName)) {
            return likes[regionName]!!
        }
        return false
    }
}