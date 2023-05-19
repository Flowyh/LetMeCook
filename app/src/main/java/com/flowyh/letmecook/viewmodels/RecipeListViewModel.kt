package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.models.RecipeDetails


class RecipeListViewModel(
  savedStateHandle: SavedStateHandle
): ViewModel() {
  private val _recipesList = (0..120).map {
    Recipe(
      title = "Recipe title",
      time = "30 min",
      difficulty = 3,
      servings = 3,
      details = RecipeDetails("test")
    )
  }

  val recipes = savedStateHandle.getStateFlow("recipes", _recipesList)
}
