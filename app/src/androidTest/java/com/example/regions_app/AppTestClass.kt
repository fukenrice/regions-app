package com.example.regions_app

import com.example.regions_app.di.AppComponent
import com.example.regions_app.di.DaggerTestAppComponent

class AppTestClass : App() {
    override fun initializeComponent(): AppComponent {
        return DaggerTestAppComponent.create()
    }
}