package com.example.showcaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.showcaseapp.ui.theme.ShowcaseAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowcaseAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = NavigationScreen.welcome
                    ) {
                        composable(NavigationScreen.welcome) {
                            WelcomeScreen(
                                goToAuth = {
                                    navController.navigate(NavigationScreen.auth)
                                },
                                goToTerms = {
                                    navController.navigate(NavigationScreen.terms)
                                }
                            )
                        }
                        composable(NavigationScreen.auth) {
                            TODO()
                        }
                        composable(NavigationScreen.terms) {
                            TODO()
                        }
                    }
                }
            }
        }
    }
}