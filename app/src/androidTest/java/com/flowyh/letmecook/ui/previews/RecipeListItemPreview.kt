package com.flowyh.letmecook.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.models.RecipeDetails
import com.flowyh.letmecook.ui.components.RecipeListItem

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreview() {
  RecipeListItem(
    recipe = Recipe(
      title = "Recipe title",
      time = "30 min",
      difficulty = 3,
      servings = 3,
      details = RecipeDetails("test")
    ),
    onRecipeClick = {}
  )
}

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreviewRight() {
  RecipeListItem(
    recipe = Recipe(
      title = "Recipe title",
      time = "30 min",
      difficulty = 3,
      servings = 3,
      details = RecipeDetails("test")
    ),
    onRecipeClick = {},
    imageOnLeft = false
  )
}
