package com.flowyh.letmecook.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flowyh.letmecook.ui.components.*
import com.flowyh.letmecook.ui.theme.LetMeCookTheme
import com.flowyh.letmecook.ui.theme.spacing
import com.flowyh.letmecook.viewmodels.RecipeFiltersViewModel
import com.flowyh.letmecook.viewmodels.RecipeListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  recipesViewModel: RecipeListViewModel = viewModel()
) {
  // Important to remember the scroll behavior state
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
  val listState = rememberLazyListState()
  val scope = rememberCoroutineScope()

  // TODO: Move or remove these callbacks
  fun onProfileClick() {}
  fun onSearchClick() {}
  fun onFilterClick() {}

  val recipesList = recipesViewModel.recipes.collectAsStateWithLifecycle()

  val navItems = bottomNavItems(
    onTodayRecipeClick = {},
    onShoppingListClick = {},
    onHomeClick = { scope.launch { listState.animateScrollToItem(0) } }, // TODO: Fix top bar color on back to top
    onFavoritesClick = {},
    onRandomClick = {}
  )

  val recipeFiltersViewModel = RecipeFiltersViewModel()
  val filters = recipeFiltersViewModel.filters

  LetMeCookTheme {
    Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        Column(Modifier.fillMaxWidth()) {
          TopAppBarSample({ onProfileClick() }, { onSearchClick() }, scrollBehavior)
        }
      },
      bottomBar = {
        BottomNavigationBar(
          modifier = Modifier.padding(MaterialTheme.spacing.default),
          items = navItems
        )
      }
    ) { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        ChipsSection(
          modifier = Modifier
            .height(32.dp)
            .fillMaxWidth(),
          filters = filters,
          selectedFilters = listOf(filters[0]),
          onFilterSelected = { onFilterClick() }
        )
        LazyColumn(
          modifier = Modifier
            .fillMaxSize(),
          state = listState
        ) {
          items(120) { item ->
            RecipeListItem(
              recipe = recipesList.value[item],
              onRecipeClick = {},
              imageOnLeft = item % 2 == 0
            )
          }
        }
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  HomeScreen()
}