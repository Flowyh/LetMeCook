package com.flowyh.letmecook.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Parcelize
data class RecipeDetails internal constructor(
  val bigImage: String,
  val description: String,
  val steps: List<String>,
  val ingredients: List<RecipeIngredient>,
  val filters: List<RecipeFilter>
) : Serializable, Parcelable

fun createRecipeDetails(
  bigImage: String,
  description: String,
  ingredients: List<RecipeIngredient>,
  steps: List<String>,
  filters: List<RecipeFilter>
): RecipeDetails? {
  if (description.isEmpty())
    return null

  if (ingredients.isEmpty())
    return null

  if (steps.isEmpty())
    return null

  return RecipeDetails(bigImage, description, steps, ingredients, filters)
}
