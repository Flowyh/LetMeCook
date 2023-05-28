package com.flowyh.letmecook.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

enum class IngredientType {
  OTHER,

}

@Parcelize
data class RecipeIngredient internal constructor(
  val name: String,
  val quantity: Double,
  val unit: String,
  val type: IngredientType
) : Serializable, Parcelable

fun createRecipeIngredient(
  name: String,
  quantity: Double,
  unit: String,
  type: IngredientType
): RecipeIngredient? {
  if (name.isEmpty())
    return null

  if (quantity <= 0)
    return null

  if (unit.isEmpty())
    return null

  return RecipeIngredient(name, quantity, unit, type)
}
