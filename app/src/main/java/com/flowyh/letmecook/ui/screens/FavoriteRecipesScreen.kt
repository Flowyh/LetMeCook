package com.flowyh.letmecook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.flowyh.letmecook.ui.components.ChipsSection
import com.flowyh.letmecook.ui.components.DefaultScreenWithoutSearchbar
import com.flowyh.letmecook.ui.components.FavoriteRecipeListItem
import com.flowyh.letmecook.ui.screens.destinations.RecipeScreenDestination
import com.flowyh.letmecook.ui.theme.spacing
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun FavoriteRecipesScreen(
  viewModel: MainBundledViewModel,
  navigator: DestinationsNavigator,
  navController: NavController,
  resultNavigator: ResultBackNavigator<Unit>
) {
  // UI state
  val listState = rememberLazyListState()

  // View model values
  val favoriteRecipesList = viewModel.favoriteRecipes.collectAsStateWithLifecycle()
  val favoriteFilters = viewModel.favoriteFilters.collectAsStateWithLifecycle()

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
      ChipsSection(
        modifier = Modifier
          .height(32.dp)
          .fillMaxWidth(),
        filters = favoriteFilters.value,
        selectedFilters = favoriteFilters.value,
        onFilterSelected = {
          viewModel.onFilterSelected(it)
        }
      )
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = MaterialTheme.spacing.tiny),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
      ) {
        items(favoriteRecipesList.value.size) { item ->
          FavoriteRecipeListItem(
            recipe = favoriteRecipesList.value[item],
            onRecipeClick = { recipe ->
              navigator.navigate(
                RecipeScreenDestination(
                  recipe = recipe
                )
              )
            },
            imageOnLeft = item % 2 == 0
          )
        }
      }
    }
  }
}