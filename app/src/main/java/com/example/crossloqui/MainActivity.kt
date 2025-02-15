package com.example.crossloqui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.compose.CrossLoquiTheme
import com.example.crossloqui.components.CrossLoquiScaffold
import com.example.crossloqui.navigation.Screen
import com.example.crossloqui.navigation.nav_graph.SetupNavGraph
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CrossLoquiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    auth = Firebase.auth
                    navController = rememberNavController()

                    // Check if user is signed in (non-null)
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        SetupNavGraph(
                            navController = navController,
                            auth = auth,
                            startScreen = Screen.Home.route
                        )
                    } else {
                        SetupNavGraph(
                            navController = navController,
                            auth = auth,
                            startScreen = Screen.Login.route
                        )
                    }
                }
            }
        }
    }
}