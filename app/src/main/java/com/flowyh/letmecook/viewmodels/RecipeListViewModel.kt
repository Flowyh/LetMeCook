package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.models.IngredientType
import com.flowyh.letmecook.models.createRecipe
import com.flowyh.letmecook.models.createRecipeDetails
import com.flowyh.letmecook.models.createRecipeIngredient


class RecipeListViewModel(
  savedStateHandle: SavedStateHandle
): ViewModel() {

  // TODO: replace with real data fetched from firebase
  //       DataRepo should be passed as a constructor parameter
  private val _recipesList = (0..120).map {
    createRecipe(
      title = "Recipe $it",
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
        filters = listOf()
      )!!
    )!!
  }

  val recipes = savedStateHandle.getStateFlow("recipes", _recipesList)
}
