package com.flowyh.letmecook.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

enum class FilterType {
  ALL,
  CUISINE,
  DIET,
  COURSE,
}

@Parcelize
data class RecipeFilter internal constructor(
  val name: String,
  val type: FilterType,
) : Serializable, Parcelable

fun createRecipeFilter(
  type: FilterType,
  name: String,
): RecipeFilter? {
  if (name.isEmpty())
    return null

  return RecipeFilter(name, type)
}
