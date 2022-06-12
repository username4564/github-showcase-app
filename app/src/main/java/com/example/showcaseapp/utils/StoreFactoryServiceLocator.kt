package com.example.showcaseapp.utils

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.showcaseapp.BuildConfig
import com.example.showcaseapp.WelcomeStore
import com.example.showcaseapp.WelcomeStoreFactory
import com.example.showcaseapp.auth.AuthStore
import com.example.showcaseapp.auth.AuthStoreFactory
import com.example.showcaseapp.data.auth.AuthRepository
import com.example.showcaseapp.domain.auth.GetAuthToken
import com.example.showcaseapp.domain.auth.GetAuthUrl
import com.example.showcaseapp.domain.auth.GetAuthUrlResult
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

    fun getAuthStore(): AuthStore {
        // TODO: ET 13.06.2022 split dependencies
        return AuthStoreFactory(
            getAuthUrl = GetAuthUrl(),
            getAuthUrlResult = GetAuthUrlResult(),
            getAuthToken = GetAuthToken(AuthRepository()),
            storeFactory = storeFactoryInstance,
            mainContext = Dispatchers.Main,
        ).create()
    }
}