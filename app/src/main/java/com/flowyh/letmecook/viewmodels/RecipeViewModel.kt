package com.flowyh.letmecook.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.flowyh.letmecook.controllers.interfaces.FirestoreRepository
import com.flowyh.letmecook.controllers.repositories.RoomRepositoryImpl
import com.flowyh.letmecook.models.*
import java.util.*
import kotlinx.coroutines.launch

class RecipeViewModel(
  private val repository: FirestoreRepository,
  private val roomRepository: RoomRepositoryImpl,
  private val savedStateHandle: SavedStateHandle
): ViewModel() {
  private var _recipesList by mutableStateOf(listOf<Recipe>())
  var recipes = savedStateHandle.getStateFlow("recipes", _recipesList)
  
  private var _filters by
    mutableStateOf(listOf(createRecipeFilter(FilterType.ALL, "All")!!))
  val filters = savedStateHandle.getStateFlow("filters", _filters)
  val activeFilters = savedStateHandle.getStateFlow("activeFilters", listOf(_filters[0]))

  private var _favoriteRecipes by mutableStateOf(listOf<Recipe>())
  val favoriteRecipes = savedStateHandle.getStateFlow("favorite", listOf<Recipe>())
  val favoriteFilters = savedStateHandle.getStateFlow("favoriteFilters", _filters)

  private var _isLoading = MutableLiveData(false)
  val isLoading: LiveData<Boolean> get() = _isLoading

  fun reloadData() {
    viewModelScope.launch {
      _isLoading.value = true
      _recipesList = repository.getAllRecipes()
      savedStateHandle["recipes"] = _recipesList

      _filters = _filters + repository.getCourseFilters()
      savedStateHandle["filters"] = _filters

      val ratedRecipes = roomRepository.getAllByRating()
      addRecipeRating(ratedRecipes)
      updateFavoriteRecipes(ratedRecipes)

      savedStateHandle["activeFilters"] = listOf(_filters[0])
      _isLoading.value = false
    }
  }

  init {
    reloadData()
  }

  private fun addRecipeRating(ratedRecipes: List<Recipe>) {
    val recipes: List<Recipe> = _recipesList
    for (recipe in recipes) {
      for (ratedRecipe in ratedRecipes) {
        if (recipe.title == ratedRecipe.title) {
          recipe.rating = ratedRecipe.rating
        }
      }
    }
    _recipesList = recipes
    savedStateHandle["recipes"] = _recipesList
  }

  private fun updateRecipeRating(rating: Float, id: String) {
    _recipesList = _recipesList.map {
      if (it.id == id) it.copy(rating = rating)
      else it
    }
    savedStateHandle["recipes"] = _recipesList
  }

  private fun updateFavoriteRecipes(favorites: List<Recipe>) {
    _favoriteRecipes = favorites
    savedStateHandle["favorite"] = _favoriteRecipes
  }

  fun isRated(id: String): Boolean {
    return _favoriteRecipes.any { it.id == id }
  }

  fun updateRating(recipe: Recipe, newRating: Float) {
    viewModelScope.launch {
      roomRepository.updateRating(recipe.copy(rating = newRating))
      updateRecipeRating(newRating, recipe.id)
      updateFavoriteRecipes(roomRepository.getAllByRating())
    }
  }

  fun insertFavoriteRecipe(recipe: Recipe) {
    viewModelScope.launch {
      roomRepository.insert(recipe)
      updateFavoriteRecipes(roomRepository.getAllByRating())
    }
  }

  fun updateRecipeList(query: String) {
    val filteredRecipes: List<Recipe> = _recipesList.filter { it.doesMatchQuery(query) }
    savedStateHandle["recipes"] = filteredRecipes
  }


  private fun filterRecipeList(filters: List<RecipeFilter>) {
    val filtersNames: List<String> = filters.map { it.name }

    if (filtersNames.contains("All")) {
      savedStateHandle["recipes"] = _recipesList
      return
    }

    val filteredRecipes: List<Recipe> = _recipesList.filter { it.doesMatchFilter(filtersNames) }
    savedStateHandle["recipes"] = filteredRecipes
  }

  fun onFilterSelected(filter: RecipeFilter) {
    var currentFilters: List<RecipeFilter> = activeFilters.value

    if (filter.name == "All")
      savedStateHandle["activeFilters"] = listOf(_filters[0])
    else {
      if (currentFilters.contains(filter)) {
        if (currentFilters.size == 1)
          savedStateHandle["activeFilters"] = listOf(_filters[0])
        else
          savedStateHandle["activeFilters"] = currentFilters.filter { it != filter }

      } else {
        if (currentFilters.contains(_filters[0]))
          currentFilters = currentFilters.filter { it != _filters[0] }

        savedStateHandle["activeFilters"] = currentFilters + filter
      }
    }

    filterRecipeList(activeFilters.value)
  }

  // Navigation bar functionality
  fun getRandomRecipe(): Recipe {
    return _recipesList.random()
  }

  fun getRecipeOfTheDay(): Recipe {
    val daysFromEpoch: Long = Calendar.getInstance().timeInMillis / (1000 * 60 * 60 * 24)
    val index: Int = (daysFromEpoch % _recipesList.size).toInt()

    return _recipesList[index]
  }
}
