package com.flowyh.letmecook.controllers.repositories

import com.flowyh.letmecook.controllers.interfaces.RoomRepositoryDao
import com.flowyh.letmecook.models.Recipe
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
  private val dao: RoomRepositoryDao
) {
  fun getRecipes(): List<Recipe> = dao.getAll()

  fun getAllByRating(): List<Recipe> = dao.getAllByRating()

  fun insertAll(recipe: Recipe) = dao.insertAll(recipe)

  fun insertAll(recipes: List<Recipe>) = dao.insertAll(*recipes.toTypedArray())

  fun updateRating(recipe: Recipe) = dao.updateRating(recipe.id, recipe.rating)

  fun deleteRecipe(recipe: Recipe) = dao.delete(recipe)

  fun deleteAll() = dao.deleteAll(dao.getAll())
}
