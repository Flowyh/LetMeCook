package com.flowyh.letmecook.models

import com.flowyh.letmecook.R

data class Recipe(
  val title: String,
  val time: String,
  val difficulty: Int,
  val servings: Int,
  val smallImage: Int = R.drawable.ic_launcher_background,
  val details: RecipeDetails,
)

// TODO: Placeholder
data class RecipeDetails(
  val placeholder: String
)
