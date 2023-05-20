package com.flowyh.letmecook.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flowyh.letmecook.ui.components.*
import com.flowyh.letmecook.viewmodels.HomeScreenViewModel
import com.flowyh.letmecook.viewmodels.RecipeFiltersViewModel
import com.flowyh.letmecook.viewmodels.RecipeListViewModel
import com.flowyh.letmecook.viewmodels.SearchBarViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
  homeScreenViewModel: HomeScreenViewModel,
) {
  val scope = rememberCoroutineScope()
  val listState = rememberLazyListState()

  fun onFilterClick() {}

  val recipesList = homeScreenViewModel.recipes.collectAsStateWithLifecycle()
  val filters = homeScreenViewModel.filters.collectAsStateWithLifecycle()

  val navItems = bottomNavItems(
    onTodayRecipeClick = {},
    onShoppingListClick = {},
    onHomeClick = {
      scope.launch {
        listState.animateScrollToItem(0)
      }
    },
    onFavoritesClick = {},
    onRandomClick = {}
  )

  DefaultScreen(
    searchBarViewModel = homeScreenViewModel.searchBarViewModel,
    bottomNavigationBarItems = navItems,
  ) {
    ChipsSection(
      modifier = Modifier
        .height(32.dp)
        .fillMaxWidth(),
      filters = filters.value,
      selectedFilters = listOf(filters.value[0]),
      onFilterSelected = { onFilterClick() }
    )
    LazyColumn(
      modifier = Modifier
        .fillMaxSize(),
      state = listState
    ) {
      items(120) { item ->
        RecipeListItem(
          recipe = recipesList.value[item]!!,
          onRecipeClick = {},
          imageOnLeft = item % 2 == 0
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  val searchBarViewModel = SearchBarViewModel()
  val recipeListViewModel: RecipeListViewModel = viewModel()
  val recipeFiltersViewModel: RecipeFiltersViewModel = viewModel()

  val homeScreenViewModel = HomeScreenViewModel(
    searchBarViewModel,
    recipeListViewModel,
    recipeFiltersViewModel
  )

  HomeScreen(homeScreenViewModel)
}
