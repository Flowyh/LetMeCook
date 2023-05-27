package com.flowyh.letmecook.controllers.repositories

import android.content.Context
import androidx.room.*
import com.flowyh.letmecook.controllers.interfaces.RoomRepositoryDao
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.models.RecipeDetails
import com.flowyh.letmecook.models.RecipeIngredient
import com.flowyh.letmecook.models.ShoppingList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@ProvidedTypeConverter
class RecipeDetailsConverter {
  @TypeConverter
  fun toDetails(value: String): RecipeDetails {
    return Gson().fromJson(value, RecipeDetails::class.java)
  }

  @TypeConverter
  fun toString(value: RecipeDetails): String {
    return Gson().toJson(value)
  }
}

@ProvidedTypeConverter
class IngredientsListConverter{
  @TypeConverter
  fun toIngredients(value: String): List<RecipeIngredient> {
    val listOfIngredients: Type = object : TypeToken<List<RecipeIngredient?>?>() {}.type
    return Gson().fromJson(value, listOfIngredients)
  }

  @TypeConverter
  fun toString(value: List<RecipeIngredient>): String {
    return Gson().toJson(value)
  }

  // ABI -> alreadyBoughtIngredients
  @TypeConverter
  fun toStringABI(value: List<Int>): String {
    return Gson().toJson(value)
  }

  @TypeConverter
  fun toABI(value: String): List<Int> {
    val listOfABI: Type = object : TypeToken<List<Int?>?>() {}.type
    return Gson().fromJson(value, listOfABI)
  }

}

@Database(entities = [Recipe::class, ShoppingList::class], version = 2)
@TypeConverters(
  RecipeDetailsConverter::class,
  IngredientsListConverter::class
)
abstract class RoomRepository : RoomDatabase() {
  abstract fun repositoryDao(): RoomRepositoryDao
}
