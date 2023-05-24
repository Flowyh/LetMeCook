package com.flowyh.letmecook.controllers.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.flowyh.letmecook.models.Recipe

@Dao
interface RoomRepositoryDao {
  @Query("SELECT * FROM favourites")
  fun getAll(): List<Recipe>

  @Query("SELECT * FROM favourites ORDER BY rating DESC")
  fun getAllByRating(): List<Recipe>

  // update the recipe's rating based on id
  @Query("UPDATE favourites SET rating = :rating WHERE id = :id")
  fun updateRating(id: String, rating: Float)

  @Insert
  fun insertAll(vararg recipes: Recipe)

  @Delete
  fun delete(recipe: Recipe)

  @Delete
  fun deleteAll(recipes: List<Recipe>)
}