package com.flowyh.letmecook.models

import android.os.Parcelable
import com.flowyh.letmecook.R
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.time.LocalDateTime

@Parcelize
data class ShoppingList internal constructor(
  val id: Int,
  val ingredients: List<RecipeIngredient>,
  val alreadyBoughtIngredients: List<Int>,
  val thumbnail: Int,
  val recipeTitle: String,
  val dateTime: LocalDateTime
) : Serializable, Parcelable

fun createShoppingList(
  id : Int,
  ingredients: List<RecipeIngredient>,
  alreadyBoughtIngredients: List<Int>,
  thumbnail: Int = R.drawable.ic_launcher_background,
  recipeTitle: String,
  dateTime: LocalDateTime
) : ShoppingList? {

  if(ingredients.isEmpty() || recipeTitle.isEmpty()){
    return null
  }

  return ShoppingList(id, ingredients, alreadyBoughtIngredients, thumbnail, recipeTitle, dateTime)
}