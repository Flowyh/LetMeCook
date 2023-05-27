package com.flowyh.letmecook.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowyh.letmecook.controllers.repositories.FirestoreRepositoryImpl
import com.flowyh.letmecook.models.*
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import kotlinx.coroutines.launch


class RecipeViewModel(
  private val savedStateHandle: SavedStateHandle
): ViewModel() {

  private val db = FirestoreRepositoryImpl()
  private var _recipesList: MutableState<List<Recipe>> = mutableStateOf(listOf())
  private val _filters: MutableState<List<RecipeFilter>> =
    mutableStateOf(listOf(createRecipeFilter(FilterType.ALL, "All")!!))


  // Recipe list
  fun loadData() {
    viewModelScope.launch {
      _recipesList.value = db.getAllRecipes()
      val newFilters = _filters.value + db.getCourseFilters()

      savedStateHandle["recipes"] = _recipesList.value
      savedStateHandle["filters"] = newFilters
      _filters.value = newFilters
    }
  }


  var recipes = savedStateHandle.getStateFlow("recipes", _recipesList.value)
  var favoriteRecipes: StateFlow<List<Recipe>> = savedStateHandle.getStateFlow("favorite", listOf())

  val filters = savedStateHandle.getStateFlow("filters", _filters.value)
  val favoriteFilters = filters // TODO: add after room is ready
  val activeFilters = savedStateHandle.getStateFlow("activeFilters", listOf(_filters.value[0]))

  fun addRecipeRating(ratedRecipes: List<Recipe>) {
    val recipes: List<Recipe> = _recipesList.value
    for (recipe in recipes) {
      for (ratedRecipe in ratedRecipes) {
        if (recipe.title == ratedRecipe.title) {
          recipe.rating = ratedRecipe.rating
        }
      }
    }
    savedStateHandle["recipes"] = recipes
  }

  fun updateRecipeRating(rating: Float, id: String) {
    _recipesList.value = _recipesList.value.map {
      if (it.id == id) it.copy(rating = rating)
      else it
    }
    savedStateHandle["recipes"] = _recipesList
  }

  fun updateFavoriteRecipes(favorites: List<Recipe>) {
    savedStateHandle["favorite"] = favorites
  }

  fun updateRecipeList(query: String) {
    val filteredRecipes: List<Recipe> = _recipesList.value.filter { it.doesMatchQuery(query) }
    savedStateHandle["recipes"] = filteredRecipes
  }


  private fun filterRecipeList(filters: List<RecipeFilter>) {
    val filtersNames: List<String> = filters.map { it.name }

    if (filtersNames.contains("All")) {
      savedStateHandle["recipes"] = _recipesList.value
      return
    }

    val filteredRecipes: List<Recipe> = _recipesList.value.filter { it.doesMatchFilter(filtersNames) }
    savedStateHandle["recipes"] = filteredRecipes
  }

  fun onFilterSelected(filter: RecipeFilter) {
    var currentFilters: List<RecipeFilter> = activeFilters.value

    if (filter.name == "All")
      savedStateHandle["activeFilters"] = listOf(_filters.value[0])
    else {
      if (currentFilters.contains(filter)) {
        if (currentFilters.size == 1)
          savedStateHandle["activeFilters"] = listOf(_filters.value[0])
        else
          savedStateHandle["activeFilters"] = currentFilters.filter { it != filter }

      } else {
        if (currentFilters.contains(_filters.value[0]))
          currentFilters = currentFilters.filter { it != _filters.value[0] }

        savedStateHandle["activeFilters"] = currentFilters + filter
      }
    }

    filterRecipeList(activeFilters.value)
  }

  // Navigation bar functionality

  // TODO: fetch all recipes from firebase
  fun getRandomRecipe(): Recipe {
    return _recipesList.value.random()
  }

  // TODO: fetch recipes from firebase
  fun getRecipeOfTheDay(): Recipe {
    val daysFromEpoch: Long = Calendar.getInstance().timeInMillis / (1000 * 60 * 60 * 24)
    val index: Int = (daysFromEpoch % _recipesList.value.size).toInt()

    return _recipesList.value[index]
  }

  // TODO: replace by getting starred recipes from room
  //       favourite recipes are a separate list (from
  //       the main recipe list)
  fun setFavouriteRecipes() {
    val starredRecipes: List<Recipe> = _recipesList.value.filter {
      it.rating > 3.5f
    }

    // sort descending by rating
    savedStateHandle["recipes"] = starredRecipes.sortedBy { it.rating }.reversed()
  }
}
