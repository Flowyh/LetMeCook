package com.flowyh.letmecook.controllers.repositories

import com.flowyh.letmecook.controllers.interfaces.RoomRepositoryDao
import com.flowyh.letmecook.models.Recipe
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
  private val dao: RoomRepositoryDao
) {
  fun getAllByRating(): List<Recipe> = dao.getAllByRating()

  fun insert(recipe: Recipe) = dao.insertAll(recipe)

  fun updateRating(recipe: Recipe) = dao.updateRating(recipe.id, recipe.rating)

  fun deleteAll() = dao.deleteAll(dao.getAll())
}
