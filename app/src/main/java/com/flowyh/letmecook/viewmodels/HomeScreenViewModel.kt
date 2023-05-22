package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class HomeScreenViewModel(
  savedStateHandle: SavedStateHandle = SavedStateHandle(),
  recipeListViewModel: RecipeListViewModel = RecipeListViewModel(savedStateHandle),
  val recipeFiltersViewModel: RecipeFiltersViewModel =
    RecipeFiltersViewModel(savedStateHandle, recipeListViewModel::filterRecipeList)
) : ViewModel() {
  val recipes = recipeListViewModel.recipes
  val filters = recipeFiltersViewModel.filters

  val searchBarViewModel = SearchBarViewModel(
    savedStateHandle,
    recipeListViewModel::updateRecipeList
  )
}
