package com.flowyh.letmecook.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.flowyh.letmecook.R
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.time.LocalDateTime

@Entity(tableName = "shoppingLists")
@Parcelize
data class ShoppingList internal constructor(
  @PrimaryKey val id: Int,
  @ColumnInfo(name = "ingredients") val ingredients: List<RecipeIngredient>,
  @ColumnInfo(name = "alreadyBoughtIngredients") var alreadyBoughtIngredients: List<Int>,
  @ColumnInfo(name = "recipeTitle") val recipeTitle: String,
  @ColumnInfo(name = "recipeId")  val recipeId: String,
  @ColumnInfo(name = "dateTime") val dateTime: Long
) : Serializable, Parcelable

fun createShoppingList(
  id : Int,
  ingredients: List<RecipeIngredient>,
  alreadyBoughtIngredients: List<Int>,
  recipeTitle: String,
  recipeId: String,
  dateTime: Long
) : ShoppingList? {

  if(ingredients.isEmpty() || recipeTitle.isEmpty()){
    return null
  }

  return ShoppingList(id, ingredients, alreadyBoughtIngredients, recipeTitle, recipeId, dateTime)
}