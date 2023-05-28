package com.flowyh.letmecook.controllers.interfaces

import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.models.RecipeFilter

interface FirestoreRepository {
  
  suspend fun getAllRecipes(): List<Recipe>

  suspend fun getRecipeByIndex(index: Int): Recipe

  suspend fun getRecipeById(recipeId: String): Recipe

  suspend fun getRecipesForFilter(filters: List<String>): List<Recipe>

  suspend fun getCourseFilters(): List<RecipeFilter>

}
