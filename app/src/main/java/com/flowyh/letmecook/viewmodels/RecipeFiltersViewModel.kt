package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import com.flowyh.letmecook.models.FilterType
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.models.RecipeFilter
import com.flowyh.letmecook.models.createRecipeFilter

class RecipeFiltersViewModel(
  private val savedStateHandle: SavedStateHandle,
  private val filterRecipeList: (List<RecipeFilter>) -> Unit
): ViewModel() {

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
  val activeFilters = savedStateHandle.getStateFlow("activeFilters", listOf(_filters[0]))

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
