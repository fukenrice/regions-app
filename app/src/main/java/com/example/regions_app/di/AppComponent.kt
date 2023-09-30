package com.example.regions_app.di

import com.example.regions_app.ui.main.RegionDetailsFragment
import com.example.regions_app.ui.main.RegionListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface AppComponent {
    fun inject(fragment: RegionDetailsFragment)
    fun inject(fragment: RegionListFragment)
}