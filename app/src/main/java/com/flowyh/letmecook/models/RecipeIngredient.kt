package com.flowyh.letmecook.models

enum class IngredientType {
  VEGETABLE,
  FRUIT,
  MEAT,
  DAIRY,
  GRAIN,
  SPICE,
  OTHER,
  // TODO: Find types for pasta, rice etc.
}

data class RecipeIngredient internal constructor(
  val name: String,
  val quantity: Double,
  val unit: String,
  val type: IngredientType
)

fun createRecipeIngredient(
  name: String,
  quantity: Double,
  unit: String,
  type: IngredientType
): RecipeIngredient? {
  if (name.isEmpty())
    return null

  if (quantity <= 0)
    return null

  if (unit.isEmpty())
    return null

  return RecipeIngredient(name, quantity, unit, type)
}
