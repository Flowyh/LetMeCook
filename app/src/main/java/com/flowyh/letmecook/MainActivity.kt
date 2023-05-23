package com.flowyh.letmecook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.flowyh.letmecook.ui.screens.HomeScreen
import com.flowyh.letmecook.ui.theme.LetMeCookTheme
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  // TODO: change to navigation to homescreen
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      LetMeCookTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          val viewModel = hiltViewModel<MainBundledViewModel>()
          HomeScreen(homeScreenViewModel = viewModel)
        }
      }
    }
  }
}
