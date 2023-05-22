package com.flowyh.letmecook.models

import com.flowyh.letmecook.R

data class Recipe internal constructor(
  val title: String,
  val time: String,
  val difficulty: Int,
  val servings: Int,
  val smallImage: Int,
  val details: RecipeDetails,
) {
  fun doesMatchDifficulty(query: String): Boolean {
    return when (query.lowercase()) {
      "easy"    -> difficulty == 1
      "medium"  -> difficulty == 2
      "hard"    -> difficulty == 3
      else      -> false
    }
  }

  fun doesMatchQuery(query: String): Boolean {
    val matchingCombinations: List<String> = listOf(
      title,
      title.replace(" ", ""),
    )

    return matchingCombinations.any {
      it.contains(query, ignoreCase = true)
    }
  }

  // TODO: replace by calling to the db for
  //       recipes with that filter?
  fun doesMatchFilter(query: List<String>): Boolean {
    val filterNames = mutableListOf<String>()
    for (filter in details.filters) {
      filterNames += filter.name
    }

    for (q in query) {
      if (filterNames.contains(q))
        return true
    }

    return false
  }

}

fun createRecipe(
  title: String,
  time: String,
  difficulty: Int,
  servings: Int,
  smallImage: Int = R.drawable.ic_launcher_background,
  details: RecipeDetails,
): Recipe? {
  if (title.isEmpty() && time.isEmpty())
    return null

  if (difficulty < 1 || difficulty > 3)
    return null

  if (servings < 1 || servings > 30)
    return null

  return Recipe(title, time, difficulty, servings, smallImage, details)
}
