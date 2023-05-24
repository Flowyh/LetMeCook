package com.flowyh.letmecook.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.flowyh.letmecook.ui.navigation.BottomBarNavigation
import com.flowyh.letmecook.ui.screens.NavGraphs
import com.flowyh.letmecook.ui.screens.appCurrentDestinationAsState
import com.flowyh.letmecook.ui.screens.destinations.Destination
import com.flowyh.letmecook.ui.screens.startAppDestination
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun BottomNavigationBar(
  modifier: Modifier = Modifier,
  navController: NavController
) {
  val currentDestination: Destination =
    navController.appCurrentDestinationAsState().value ?: NavGraphs.root.startAppDestination

  NavigationBar(
    modifier = modifier
  ) {
    BottomBarNavigation.values().forEach { destination ->
      NavigationBarItem(
        icon = { Icon(destination.icon, destination.contentDescription) },
        selected = destination.direction == currentDestination,
        onClick = {
          navController.navigate(destination.direction, fun NavOptionsBuilder.() {
            launchSingleTop = true
          })
        }
      )
    }
  }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
  BottomNavigationBar(
    navController = rememberAnimatedNavController()
  )
}
