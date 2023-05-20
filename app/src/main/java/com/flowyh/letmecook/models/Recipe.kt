package com.flowyh.letmecook.models

import com.flowyh.letmecook.R

data class Recipe internal constructor(
  val title: String,
  val time: String,
  val difficulty: Int,
  val servings: Int,
  val smallImage: Int,
  val details: RecipeDetails,
)

fun createRecipe(
  title: String,
  time: String,
  difficulty: Int,
  servings: Int,
  smallImage: Int = R.drawable.ic_launcher_background,
  details: RecipeDetails,
): Recipe? {
  if (title.isEmpty() && time.isEmpty())
    return null

  if (difficulty < 1 || difficulty > 3)
    return null

  if (servings < 1 || servings > 30)
    return null

  return Recipe(title, time, difficulty, servings, smallImage, details)
}
