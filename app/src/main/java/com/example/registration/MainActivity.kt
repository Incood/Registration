package com.example.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.registration.domain.repository.SharedPrefRepository
import com.example.registration.presentation.navigation.NavGraph
import com.example.registration.presentation.navigation.Screens
import com.example.registration.ui.theme.RegistrationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPrefRepository: SharedPrefRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegistrationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val startDestination = if (sharedPrefRepository.isUserAuthorized()) {
                        Screens.ListUsers.route
                    } else {
                        Screens.NoAuthorization.route
                    }
                    NavGraph(navController = navController, startDestination = startDestination)
                }
            }
        }
    }
}