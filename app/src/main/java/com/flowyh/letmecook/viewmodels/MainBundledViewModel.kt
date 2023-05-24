package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.controllers.interfaces.FirestoreRepository
import com.flowyh.letmecook.controllers.repositories.RoomRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainBundledViewModel @Inject constructor(
  private val repository: FirestoreRepository,
  private val savedStateHandle: SavedStateHandle
) : ViewModel() {
  @Inject
  lateinit var roomRepository: RoomRepositoryImpl

  val recipeViewModel: RecipeViewModel = RecipeViewModel(savedStateHandle)

  val recipes = recipeViewModel.recipes
  val favoriteRecipes = recipeViewModel.favoriteRecipes

  val filters = recipeViewModel.filters
  val favoriteFilters = recipeViewModel.favoriteFilters
  val activeFilters = recipeViewModel.activeFilters

  val onFilterSelected = recipeViewModel::onFilterSelected
  val onRecipeListRefresh = recipeViewModel::onRecipeListRefresh

  val searchBarViewModel = SearchBarViewModel(
    savedStateHandle,
    recipeViewModel::updateRecipeList
  )
}
