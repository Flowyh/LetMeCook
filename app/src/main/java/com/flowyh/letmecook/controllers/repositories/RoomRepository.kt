package com.flowyh.letmecook.controllers.repositories

import androidx.room.*
import androidx.room.RoomDatabase
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.controllers.interfaces.RoomRepositoryDao
import com.flowyh.letmecook.models.RecipeDetails
import com.google.gson.Gson

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

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(
  RecipeDetailsConverter::class,
)
abstract class RoomRepository : RoomDatabase() {
  abstract fun repositoryDao(): RoomRepositoryDao
}
