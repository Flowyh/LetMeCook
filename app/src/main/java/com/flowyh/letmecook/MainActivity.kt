package com.flowyh.letmecook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import com.flowyh.letmecook.ui.screens.NavGraphs
import com.flowyh.letmecook.ui.theme.LetMeCookTheme
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      LetMeCookTheme {
        Surface(
          modifier = Modifier
            .fillMaxSize()
        ) {
          val navController = rememberAnimatedNavController()
          val width = LocalConfiguration.current.screenWidthDp

          val viewModel = hiltViewModel<MainBundledViewModel>(this@MainActivity)

          val navHostEngine = rememberAnimatedNavHostEngine(
            navHostContentAlignment = Alignment.TopCenter,
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
              enterTransition = {
                slideInHorizontally(
                  initialOffsetX = { width },
                  animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                  )
                )
              },
              exitTransition = {
                slideOutHorizontally(
                  targetOffsetX = { -width },
                  animationSpec = tween(
                    durationMillis = 100,
                  )
                )
              },
              popEnterTransition = {
                slideInHorizontally(
                  initialOffsetX = { -width },
                  animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                  )
                )
              },
              popExitTransition = {
                slideOutHorizontally(
                  targetOffsetX = { width },
                  animationSpec = tween(
                    durationMillis = 100
                  )
                )
              }
            )
          )

          DestinationsNavHost(
            navGraph = NavGraphs.root,
            navController = navController,
            engine = navHostEngine,
            dependenciesContainerBuilder = {
              dependency(viewModel)
            }
          )
        }
      }
    }
  }
}
