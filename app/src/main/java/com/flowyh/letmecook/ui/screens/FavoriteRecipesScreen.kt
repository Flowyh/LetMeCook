package com.flowyh.letmecook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.flowyh.letmecook.ui.components.DefaultScreenWithoutSearchbar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun FavoriteRecipesScreen(
  navController: NavController,
  resultNavigator: ResultBackNavigator<Unit>
) {
  DefaultScreenWithoutSearchbar(
    navController = navController,
    onNavigationClick = {
      resultNavigator.navigateBack()
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      // TODO: add content
    }
  }
}