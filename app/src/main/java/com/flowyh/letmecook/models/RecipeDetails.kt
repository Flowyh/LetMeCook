package com.flowyh.letmecook.models

import android.os.Parcelable
import android.util.Log
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.flowyh.letmecook.R
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.lang.reflect.Type

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
