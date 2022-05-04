package com.example.showcaseapp.utils

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.showcaseapp.BuildConfig
import com.example.showcaseapp.WelcomeStore
import com.example.showcaseapp.WelcomeStoreFactory
import kotlinx.coroutines.Dispatchers

internal object StoreFactoryServiceLocator {
    private val storeFactoryInstance by lazy {
        if (BuildConfig.DEBUG) {
            // TODO: ET 01.06.2022 implement time travel store?
//            LoggingStoreFactory(delegate = TimeTravelStoreFactory())
            DefaultStoreFactory()
        } else {
            DefaultStoreFactory()
        }
    }

    fun getWelcomeStore(): WelcomeStore {
        return WelcomeStoreFactory(
            storeFactory = storeFactoryInstance,
            mainContext = Dispatchers.Main,
        ).create()
    }
}