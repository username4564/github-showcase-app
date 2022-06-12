package com.example.showcaseapp.utils

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.showcaseapp.domain.auth.SetAuthToken
import kotlinx.coroutines.Dispatchers

@SuppressLint("StaticFieldLeak")
internal object StoreFactoryServiceLocator {
    private lateinit var context: Context

    private val storeFactoryInstance by lazy {
        if (BuildConfig.DEBUG) {
            // TODO: ET 01.06.2022 implement time travel store?
//            LoggingStoreFactory(delegate = TimeTravelStoreFactory())
            DefaultStoreFactory()
        } else {
            DefaultStoreFactory()
        }
    }

    fun init(context: Context) {
        this.context = context
    }

    fun getWelcomeStore(): WelcomeStore {
        return WelcomeStoreFactory(
            storeFactory = storeFactoryInstance,
            mainContext = Dispatchers.Main,
        ).create()
    }

    fun getAuthStore(): AuthStore {
        // TODO: ET 13.06.2022 split dependencies
        val authRepository = AuthRepository(context)
        return AuthStoreFactory(
            getAuthUrl = GetAuthUrl(),
            getAuthUrlResult = GetAuthUrlResult(),
            getAuthToken = GetAuthToken(authRepository),
            setAuthToken = SetAuthToken(authRepository),
            storeFactory = storeFactoryInstance,
            ioContext = Dispatchers.IO,
            mainContext = Dispatchers.Main,
        ).create()
    }
}