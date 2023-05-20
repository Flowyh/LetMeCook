package com.flowyh.letmecook.models

import com.flowyh.letmecook.R

data class RecipeDetails internal constructor(
  val bigImage: Int,
  val description: String,
  val steps: List<String>,
  val ingredients: List<RecipeIngredient>,
  val rating: Float,
  val filters: List<RecipeFilter>
)

fun createRecipeDetails(
  bigImage: Int = R.drawable.ic_launcher_background,
  description: String,
  ingredients: List<RecipeIngredient>,
  steps: List<String>,
  rating: Float,
  filters: List<RecipeFilter>
): RecipeDetails? {
  if (description.isEmpty())
    return null

  if (ingredients.isEmpty())
    return null

  if (steps.isEmpty())
    return null

  if (rating < 0 || rating > 5)
    return null

  return RecipeDetails(bigImage, description, steps, ingredients, rating, filters)
}
