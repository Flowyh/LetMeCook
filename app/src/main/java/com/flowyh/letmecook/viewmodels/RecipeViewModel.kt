package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.models.*


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
        ingredients = listOf(createRecipeIngredient(
          name = "Ingredient $it",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.OTHER
        )!!),
        steps = listOf("Step 1", "Step 2", "Step 3"),
        rating = 4.5f,
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

  fun updateRecipeList(query: String) {
    val filteredRecipes: List<Recipe> = _recipesList.filter { it.doesMatchQuery(query) }
    savedStateHandle["recipes"] = filteredRecipes
  }

  fun filterRecipeList(filters: List<RecipeFilter>) {
    val filtersNames: List<String> = filters.map { it.name }

    if (filtersNames.contains("All")) {
      savedStateHandle["recipes"] = _recipesList
      return
    }

    val filteredRecipes: List<Recipe> = _recipesList.filter { it.doesMatchFilter(filtersNames) }
    savedStateHandle["recipes"] = filteredRecipes
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
  val activeFilters =
    savedStateHandle.getStateFlow("activeFilters", listOf(_filters[0]))

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
}
