package com.flowyh.letmecook.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flowyh.letmecook.ui.components.*
import com.flowyh.letmecook.ui.screens.destinations.RecipeScreenDestination
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListMainScreen(
  viewModel: MainBundledViewModel,
  navigator: DestinationsNavigator
) {
  val scope = rememberCoroutineScope()
  val listState = rememberLazyListState()

  val recipesList = viewModel.recipes.collectAsStateWithLifecycle()
  val filters = viewModel.filters.collectAsStateWithLifecycle()
  val activeFilters = viewModel.activeFilters.collectAsStateWithLifecycle()

  val navItems = bottomNavItems(
    onTodayRecipeClick = {
      navigator.navigate(
        RecipeScreenDestination(
          recipe = viewModel.recipeViewModel.getRecipeOfTheDay()
        )
      )
    },
    onShoppingListClick = {},
    onHomeClick = {
      scope.launch {
        listState.animateScrollToItem(0)
      }
    },
    onFavoritesClick = {},
    onRandomClick = {
      navigator.navigate(
        RecipeScreenDestination(
          recipe = viewModel.recipeViewModel.getRandomRecipe()
        )
      )
    }
  )

  DefaultScreen(
    searchBarViewModel = viewModel.searchBarViewModel,
    bottomNavigationBarItems = navItems,
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
        filters = filters.value,
        selectedFilters = activeFilters.value,
        onFilterSelected = {
          viewModel.onFilterSelected(it)
        }
      )
      LazyColumn(
        modifier = Modifier
          .fillMaxSize(),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(1.dp)
      ) {
        items(recipesList.value.size) { item ->
          RecipeListItem(
            recipe = recipesList.value[item],
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
