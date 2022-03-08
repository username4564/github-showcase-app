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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.showcaseapp.ui.theme.ShowcaseAppTheme
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.backpress.LocalBackPressHandler
import com.github.zsoltk.compose.router.Router

sealed class Routing {
    object Screen1 : Routing()
    object Screen2 : Routing()
    object Screen3 : Routing()
}

class ComposeRouterMainActivity : ComponentActivity() {
    private val backPressHandler = BackPressHandler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowcaseAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting("Android")

                    val defaultRouting: Routing = Routing.Screen1

                    CompositionLocalProvider(
                        LocalBackPressHandler provides backPressHandler,
                    ) {
                        Router(defaultRouting = defaultRouting) { backStack ->
                            when (val routing = backStack.last()) {
                                is Routing.Screen1 -> Greeting(
                                    name = "Android1",
                                    onClick = {
                                        // add a new routing to the back stack:
                                        backStack.push(Routing.Screen2)
                                    }
                                )

                                is Routing.Screen2 -> Greeting(
                                    name = "Android2",
                                    onClick = {
                                        // add a new routing to the back stack:
                                        backStack.push(Routing.Screen3)
                                    }
                                )

                                is Routing.Screen3 -> Greeting(
                                    name = "Android3",
                                    onClick = {
                                        // add a new routing to the back stack:
                                        backStack.push(Routing.Screen1)
                                    })
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onBackPressed() {
        if (!backPressHandler.handle()) {
            super.onBackPressed()
        }
    }
}

@Composable
fun Greeting(name: String, onClick: () -> Unit) {
//    Text(text = "Hello $name!")

    Text(
        text = "Next screen $name",
        modifier = Modifier.clickable {
            onClick()
        }
    )

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
fun DefaultPreview() {
    ShowcaseAppTheme {
        Greeting("Android", {})
    }
}