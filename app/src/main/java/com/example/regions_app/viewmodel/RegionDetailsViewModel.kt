package com.example.regions_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.regions_app.data.model.RegionModel
import com.example.regions_app.data.utils.Likes
import javax.inject.Inject
import javax.inject.Singleton

// По-хорошему надо сделать виь модель синглтоном, чтобы при смене конфигурации она не пересоздавалась вместе с фрагментом,
// но если сделать так, будет выскакивать налл поинтер в лайвдате. Пару часов пробовл разобраться в чем дело, но так и не понял.
// В свободное время попробую еще покопать, но сейчас решил просто сделать у приложения только портретную ориентацию, чтобы не пересоздавалась вью модель.
// Надеюсь, это не критично)
//@Singleton
class RegionDetailsViewModel @Inject constructor(private val likes: Likes) : ViewModel() {
    val liked = MutableLiveData<Boolean>(false)
    var region: RegionModel? = null

    fun setLiked() {
        likes.changeLike(region!!.title)
        this.liked.postValue(likes.getRegion(region!!.title))
    }

    fun initLike() {
        liked.postValue(likes.getRegion(region!!.title))
    }
}