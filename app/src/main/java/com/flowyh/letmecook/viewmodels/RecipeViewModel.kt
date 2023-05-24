package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.models.*
import java.util.*
import kotlin.random.Random.Default.nextInt


class RecipeViewModel(
  private val savedStateHandle: SavedStateHandle
): ViewModel() {

  // Recipe list

  // TODO: replace with real data fetched from firebase
  //       DataRepo should be passed as a constructor parameter
  private var _recipesList: List<Recipe> = (0..120).map {
    createRecipe(
      title =
        if (it % 5 == 0) {
          "Recipe $it (Breakfast)"
        } else if (it % 5 == 1) {
          "Recipe $it (Lunch)"
        } else if (it % 5 == 2) {
          "Recipe $it (Dinner)"
        } else if (it % 5 == 3) {
          "Recipe $it (Snack)"
        } else {
          "Recipe $it (Dessert)"
        },
      time = "30 min",
      difficulty = 3,
      servings = 3,
      details = createRecipeDetails(
        description = "Recipe $it",
        ingredients = listOf(
          createRecipeIngredient(
            name = "Ingredient 1",
            quantity = 0.5,
            unit = "kg",
            type = IngredientType.OTHER
          )!!,
          createRecipeIngredient(
            name = "Ingredient 2",
            quantity = 4.0,
            unit = "cups",
            type = IngredientType.OTHER
          )!!,
          createRecipeIngredient(
            name = "Ingredient 3",
            quantity = 3.0,
            unit = "tsp",
            type = IngredientType.OTHER
          )!!,
        ),
        steps = listOf(
          "Cook it",
          "Throw me some numbers",
          "Super loooooooooooooooooooong looooooooooooooooooooooooooooooooooooong liiiiiiiiiiiiiiiiiiiiine"
        ),
        rating = nextInt(1, 11).toFloat() / 2f,
        filters =
          if (it % 5 == 0) {
            listOf(
              createRecipeFilter(FilterType.COURSE, "Breakfast")!!,
            )
          } else if (it % 5 == 1) {
            listOf(
              createRecipeFilter(FilterType.COURSE, "Lunch")!!,
            )
          } else if (it % 5 == 2) {
            listOf(
              createRecipeFilter(FilterType.COURSE, "Dinner")!!,
            )
          } else if (it % 5 == 3) {
            listOf(
              createRecipeFilter(FilterType.COURSE, "Snack")!!,
            )
          } else {
            listOf(
              createRecipeFilter(FilterType.COURSE, "Dessert")!!,
            )
          },
      )!!
    )!!
  }

  var recipes = savedStateHandle.getStateFlow("recipes", _recipesList)

  // TODO: Replace with data from room
  var favoriteRecipes = recipes

  fun updateRecipeList(query: String) {
    val filteredRecipes: List<Recipe> = _recipesList.filter { it.doesMatchQuery(query) }
    savedStateHandle["recipes"] = filteredRecipes
  }

  // TODO: implement when DB is ready
  fun onRecipeListRefresh() {

  }

  // Filtering recipes

  // TODO: replace with real data fetched from firebase
  //       DataRepo should be passed as a constructor parameter
  private val _filters = listOf(
    createRecipeFilter(FilterType.ALL, "All")!!,
    createRecipeFilter(FilterType.COURSE, "Breakfast")!!,
    createRecipeFilter(FilterType.COURSE, "Lunch")!!,
    createRecipeFilter(FilterType.COURSE, "Dinner")!!,
    createRecipeFilter(FilterType.COURSE, "Snack")!!,
    createRecipeFilter(FilterType.COURSE, "Dessert")!!
  )

  val filters = savedStateHandle.getStateFlow("filters", _filters)
  val favoriteFilters = filters // TODO: add after room is ready
  val activeFilters = savedStateHandle.getStateFlow("activeFilters", listOf(_filters[0]))

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

  // TODO: move this functions to the navigation bar
  //       handler / pass it as a constructor parameter
  // TODO: fetch all recipes from firebase
  fun getRandomRecipe(): Recipe {
    return _recipesList.random()
  }

  // TODO: fetch recipes from firebase
  fun getRecipeOfTheDay(): Recipe {
    val daysFromEpoch: Long = Calendar.getInstance().timeInMillis / (1000 * 60 * 60 * 24)
    val index: Int = (daysFromEpoch % _recipesList.size).toInt()

    return _recipesList[index]
  }

  // TODO: replace by getting starred recipes from room
  //       favourite recipes are a sepatare list (from
  //       the main recipe list)
  fun setFavouriteRecipes() {
    val starredRecipes: List<Recipe> = _recipesList.filter {
      it.details.rating > 3.5f
    }

    // sort descending by rating
    savedStateHandle["recipes"] = starredRecipes.sortedBy { it.details.rating }.reversed()
  }
}
