package com.flowyh.letmecook.viewmodels

import com.flowyh.letmecook.ui.components.Filter

class RecipeFiltersViewModel {
  val filters = listOf(
    Filter("All", true),
    Filter("Breakfast", false),
    Filter("Lunch", false),
    Filter("Dinner", false),
    Filter("Snack", false),
    Filter("Dessert", false),
  )
}