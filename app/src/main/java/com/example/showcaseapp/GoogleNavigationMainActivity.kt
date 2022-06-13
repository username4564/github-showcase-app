package com.example.showcaseapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.showcaseapp.ui.theme.ShowcaseAppTheme

object GoogleNavigationScreen {
    const val profile = "profile"
    const val friendsList = "friendslist"
}

class GoogleNavigationMainActivity : ComponentActivity() {
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
                        startDestination = GoogleNavigationScreen.profile
                    ) {
                        composable(GoogleNavigationScreen.profile) {
                            GreetingQ(name = "Profile") {
                                navController.navigate(GoogleNavigationScreen.friendsList)
                            }
                        }
                        composable(GoogleNavigationScreen.friendsList) {
                            GreetingQ(name = "FriendsList") {
                                navController.navigate(GoogleNavigationScreen.profile)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingQ(name: String, navigation: () -> Unit) {
    val model: MainViewModel = viewModel()
    Log.d("XX", "Model $model")

    LogComposableLifecycle(name)

    Text(
        text = "Hello $name!",
        modifier = Modifier.clickable {
            navigation()
        }
    )
}

@Composable
private fun LogComposableLifecycle(name: String) {
    SideEffect {
        Log.d("XX", "created $name")
    }
    DisposableEffect(Unit) {
        onDispose {
            Log.d("XX", "disposed $name")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreviewQ() {
    ShowcaseAppTheme {
        Greeting("Android") {}
    }
}

class MainViewModel : ViewModel() {

    val counterLiveDate: LiveData<Int>
        get() = counter

    private val counter = MutableLiveData<Int>()
    private var count = 0

    fun increaseCounter() {
        counter.value = ++count
    }

    override fun onCleared() {
        Log.d("XX", "ViewModel: onCleared $this")
        super.onCleared()
    }
}