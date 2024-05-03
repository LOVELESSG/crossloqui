package com.example.crossloqui.ui.homepage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.crossloqui.components.CrossLoquiAppBar
import com.example.crossloqui.components.CrossLoquiNavigationBar
import com.example.crossloqui.components.CrossLoquiScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomepageScreen(
    navController: NavController
) {
    /*Scaffold(
        topBar = { CrossLoquiAppBar(title = { Text(text = "Message") }) },
        bottomBar = { CrossLoquiNavigationBar(navController) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Text(
            modifier = Modifier.padding(paddingValues),
            text = "Hello Android!"
        )
    }*/
    CrossLoquiScaffold(navController = navController) {PaddingValues ->
        Text(
            modifier = Modifier.padding(PaddingValues),
            text = "Hello Android!"
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomepageScreenPreview() {
    HomepageScreen(navController = rememberNavController())
}