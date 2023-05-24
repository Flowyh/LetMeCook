package com.flowyh.letmecook.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.flowyh.letmecook.ui.components.*
import com.flowyh.letmecook.ui.screens.destinations.RecipeScreenDestination
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun RecipeListScreen(
  viewModel: MainBundledViewModel,
  navigator: DestinationsNavigator,
  navController: NavController,
  resultRecipient: ResultRecipient<RecipeScreenDestination, Float>
) {
  // UI state
  val listState = rememberLazyListState()

  // View model values
  val recipesList = viewModel.recipes.collectAsStateWithLifecycle()
  val filters = viewModel.filters.collectAsStateWithLifecycle()
  val activeFilters = viewModel.activeFilters.collectAsStateWithLifecycle()

  // Refreshing
  val refreshScope = rememberCoroutineScope()
  var isRefreshing by remember { mutableStateOf(false) }
  fun refresh() = refreshScope.launch {
    isRefreshing = true
    viewModel.onRecipeListRefresh()
    isRefreshing = false
  }

  val refreshState = rememberPullRefreshState(
    refreshing = isRefreshing,
    onRefresh = ::refresh,
    refreshThreshold = (LocalConfiguration.current.screenHeightDp / 13).dp
  )

  // Navigator result
  resultRecipient.onNavResult { result ->
    when (result) {
      is NavResult.Canceled -> {
        // TODO: Handle cancel
        Log.d("RecipeListMainScreen", "onNavResult: canceled")
      }
      is NavResult.Value -> {
        // TODO: Handle rating, add to room or sth
        Log.d("RecipeListMainScreen", "onNavResult: ${result.value}")
      }
    }
  }

  DefaultScreenWithSearchbar(
    searchBarViewModel = viewModel.searchBarViewModel,
    onTodayRecipeClick = {
      navigator.navigate(
        RecipeScreenDestination(
          recipe = viewModel.recipeViewModel.getRecipeOfTheDay()
        )
      )
    },
    onRandomRecipeClick = {
      navigator.navigate(
        RecipeScreenDestination(
          recipe = viewModel.recipeViewModel.getRandomRecipe()
        )
      )
    },
    navController = navController
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
      Box(
        modifier = Modifier.pullRefresh(refreshState)
      ) {
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

        PullRefreshIndicator(
          modifier = Modifier.align(Alignment.TopCenter),
          refreshing = isRefreshing,
          state = refreshState,
          backgroundColor = MaterialTheme.colorScheme.primaryContainer,
          contentColor = MaterialTheme.colorScheme.primary
        )
      }
    }
  }
}
