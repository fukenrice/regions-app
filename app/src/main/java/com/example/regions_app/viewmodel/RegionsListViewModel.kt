package com.example.regions_app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.regions_app.data.api.ApiService
import com.example.regions_app.data.model.RegionModel
import com.example.regions_app.data.utils.Likes
import com.example.regions_app.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "RegionsListViewModel"

@Singleton
class RegionsListViewModel @Inject constructor(val api: ApiService, val likes: Likes) : ViewModel() {
    val regionsList = MutableLiveData<Resource<List<RegionModel>>>(
        Resource.loading(
            listOf()
        )
    )

    fun getRegions() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                regionsList.postValue(Resource.loading(listOf()))
                val regions = api.getRegions()
                likes.initializeRegions(regions.regions)
                regionsList.postValue(Resource.success(regions.regions))
            } catch (e: Throwable) {
                regionsList.postValue(
                    Resource.error(
                        "Ошибка получения данных, проверьте подключение к сети",
                        listOf()
                    )
                )
                Log.d(TAG, "getRegions: " + e.message)
            }
        }
    }

    fun isEmpty(): Boolean {
        if (regionsList.value != null && regionsList.value!!.data != null)  {
            return regionsList.value!!.data!!.isEmpty()
        }
        return true
    }
}