package com.flowyh.letmecook.models

enum class FilterType {
  CUISINE,
  DIET,
  COURSE,
}

data class RecipeFilter internal constructor(
  val name: String,
  val type: FilterType,
)

fun createRecipeFilter(
  type: FilterType,
  name: String,
): RecipeFilter? {
  if (name.isEmpty())
    return null

  return RecipeFilter(name, type)
}
