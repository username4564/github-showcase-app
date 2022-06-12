package com.example.showcaseapp

import android.app.Application
import com.example.showcaseapp.utils.StoreFactoryServiceLocator

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        StoreFactoryServiceLocator.init(this)
    }

}