package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.ViewModel

class HomeScreenViewModel(
  val searchBarViewModel: SearchBarViewModel,
  recipeListViewModel: RecipeListViewModel,
  recipeFiltersViewModel: RecipeFiltersViewModel
) : ViewModel() {
  val recipes = recipeListViewModel.recipes
  val filters = recipeFiltersViewModel.filters
}
