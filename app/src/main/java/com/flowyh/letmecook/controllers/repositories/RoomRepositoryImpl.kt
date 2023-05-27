package com.flowyh.letmecook.controllers.repositories

import androidx.room.Delete
import androidx.room.Insert
import com.flowyh.letmecook.controllers.interfaces.RoomRepositoryDao
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.models.ShoppingList
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
  private val dao: RoomRepositoryDao
) {
  fun getAllByRating(): List<Recipe> = dao.getAllByRating()

  fun insert(recipe: Recipe) = dao.insertAll(recipe)

  fun updateRating(recipe: Recipe) = dao.updateRating(recipe.id, recipe.rating)

  fun deleteAll() = dao.deleteAll(dao.getAll())

  fun getShoppingLists() = dao.getAllShoppingLists()

  fun updateAlreadyBoughtIngredients(id: Int, alreadyBought: List<Int>) = dao.updateAlreadyBoughtIngredients(id, alreadyBought)

  fun insertAllShoppingLists(lists: List<ShoppingList>) = dao.insertAllShoppingLists(*lists.toTypedArray())

  fun delete(list: ShoppingList) = dao.delete(list)

}
