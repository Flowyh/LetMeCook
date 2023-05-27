package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.controllers.interfaces.FirestoreRepository
import com.flowyh.letmecook.controllers.repositories.RoomRepositoryImpl
import com.flowyh.letmecook.models.ShoppingList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainBundledViewModel @Inject constructor(
  private val repository: FirestoreRepository,
  private val savedStateHandle: SavedStateHandle,
  val roomRepository: RoomRepositoryImpl
) : ViewModel() {

  val recipeViewModel: RecipeViewModel = RecipeViewModel(savedStateHandle)

  val recipes = recipeViewModel.recipes
  val favoriteRecipes = recipeViewModel.favoriteRecipes

  val filters = recipeViewModel.filters
  val favoriteFilters = recipeViewModel.favoriteFilters
  val activeFilters = recipeViewModel.activeFilters

  val onFilterSelected = recipeViewModel::onFilterSelected
  val onRecipeListRefresh = recipeViewModel::loadData
  val loadRecipes = recipeViewModel::loadData

  // TODO: Add shopping list stuff (view model or fields)
  val shoppingListViewModel = ShoppingListViewModel(savedStateHandle, roomRepository)
  val shoppingLists = shoppingListViewModel.shoppingListsState

  val searchBarViewModel = SearchBarViewModel(
    savedStateHandle,
    recipeViewModel::updateRecipeList
  )

  init {
    recipeViewModel.addRecipeRating(roomRepository.getAllByRating())
    recipeViewModel.updateFavoriteRecipes(roomRepository.getAllByRating())
  }
}
