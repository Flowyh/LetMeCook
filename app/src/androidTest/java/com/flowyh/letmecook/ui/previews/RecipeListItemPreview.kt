package com.flowyh.letmecook.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.flowyh.letmecook.models.*
import com.flowyh.letmecook.ui.components.RecipeListItem

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreview() {
  RecipeListItem(
    recipe = createRecipe(
      title = "Recipe title",
      time = "30 min",
      difficulty = 3,
      servings = 3,
      details = createRecipeDetails(
        description = "Recipe",
        ingredients = listOf(createRecipeIngredient(
          name = "Ingredient",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.OTHER
        )!!),
        steps = listOf("Step 1", "Step 2", "Step 3"),
        rating = 4.5f,
        filters = listOf()
      )!!
    )!!,
    onRecipeClick = {}
  )
}

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreviewRight() {
  RecipeListItem(
    recipe = createRecipe(
      title = "Recipe title",
      time = "30 min",
      difficulty = 3,
      servings = 3,
      details = createRecipeDetails(
        description = "Recipe",
        ingredients = listOf(createRecipeIngredient(
          name = "Ingredient",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.OTHER
        )!!),
        steps = listOf("Step 1", "Step 2", "Step 3"),
        rating = 4.5f,
        filters = listOf()
      )!!
    )!!,
    onRecipeClick = {},
    imageOnLeft = false
  )
}
