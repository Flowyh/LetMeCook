package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.ui.components.Filter

class RecipeFiltersViewModel(
  savedStateHandle: SavedStateHandle
): ViewModel() {

  // TODO: replace with real data fetched from firebase
  //       DataRepo should be passed as a constructor parameter
  private val _filters = listOf(
    Filter("All", true),
    Filter("Breakfast", false),
    Filter("Lunch", false),
    Filter("Dinner", false),
    Filter("Snack", false),
    Filter("Dessert", false),
  )

  val filters = savedStateHandle.getStateFlow("filters", _filters)
}