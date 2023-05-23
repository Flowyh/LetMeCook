package com.flowyh.letmecook.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flowyh.letmecook.ui.components.*
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  homeScreenViewModel: MainBundledViewModel,
) {
  val scope = rememberCoroutineScope()
  val listState = rememberLazyListState()

  val recipesList = homeScreenViewModel.recipes.collectAsStateWithLifecycle()
  val filters = homeScreenViewModel.filters.collectAsStateWithLifecycle()
  val activeFilters = homeScreenViewModel.activeFilters.collectAsStateWithLifecycle()

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
      selectedFilters = activeFilters.value,
      onFilterSelected = {
        homeScreenViewModel.onFilterSelected(it)
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
