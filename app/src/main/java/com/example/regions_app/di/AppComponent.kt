package com.example.regions_app.di

import com.example.regions_app.ui.RegionDetailsFragment
import com.example.regions_app.viewmodel.RegionsListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface AppComponent {
    fun inject(fragment: RegionDetailsFragment)
    fun inject(fragment: RegionsListViewModel)
}