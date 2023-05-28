package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.controllers.interfaces.FirestoreRepository
import com.flowyh.letmecook.controllers.repositories.RoomRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainBundledViewModel @Inject constructor(
  firestoreRepository: FirestoreRepository,
  roomRepository: RoomRepositoryImpl,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

  val recipeViewModel: RecipeViewModel =
    RecipeViewModel(firestoreRepository, roomRepository, savedStateHandle)

  val isLoading = recipeViewModel.isLoading

  val recipes = recipeViewModel.recipes
  val favoriteRecipes = recipeViewModel.favoriteRecipes

  val isRated = recipeViewModel::isRated
  val insertFavoriteRecipe = recipeViewModel::insertFavoriteRecipe
  val updateRating = recipeViewModel::updateRating

  val filters = recipeViewModel.filters
  val favoriteFilters = recipeViewModel.favoriteFilters
  val activeFilters = recipeViewModel.activeFilters

  val onFilterSelected = recipeViewModel::onFilterSelected
  val onRecipeListRefresh = recipeViewModel::reloadData

  val shoppingListViewModel = ShoppingListViewModel(
    roomRepository,
    savedStateHandle,
    recipeViewModel::getRecipeById
  )
  val shoppingLists = shoppingListViewModel.shoppingListsState

  val searchBarViewModel = SearchBarViewModel(
    savedStateHandle,
    recipeViewModel::updateRecipeList
  )
}
