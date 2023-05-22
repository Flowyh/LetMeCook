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
import com.flowyh.letmecook.ui.components.*
import com.flowyh.letmecook.viewmodels.HomeScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
  homeScreenViewModel: HomeScreenViewModel,
) {
  val scope = rememberCoroutineScope()
  val listState = rememberLazyListState()

  val recipesList = homeScreenViewModel.recipes.collectAsStateWithLifecycle()
  val filters = homeScreenViewModel.filters.collectAsStateWithLifecycle()
  val _activeFilters = homeScreenViewModel.recipeFiltersViewModel
                        .activeFilters.collectAsStateWithLifecycle()

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
      selectedFilters = _activeFilters.value,
      onFilterSelected = {
        homeScreenViewModel.recipeFiltersViewModel.onFilterSelected(it)
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
  val homeScreenViewModel = HomeScreenViewModel()

  HomeScreen(homeScreenViewModel)
}
