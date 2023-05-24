package com.flowyh.letmecook.models

import android.os.Parcelable
import androidx.room.*
import com.flowyh.letmecook.R
import kotlinx.parcelize.Parcelize
import java.io.Serializable
import java.util.UUID

@Entity(tableName = "favourites")
@Parcelize
data class Recipe internal constructor(
  @ColumnInfo(name = "title") val title: String,
  @ColumnInfo(name = "time") val time: String,
  @ColumnInfo(name = "difficulty") val difficulty: Int,
  @ColumnInfo(name = "servings") val servings: Int,
  @ColumnInfo(name = "rating") val rating: Float,
  @ColumnInfo(name = "smallImage") val smallImage: Int,
  @ColumnInfo(name = "details") val details: RecipeDetails,
  @PrimaryKey val id: String
) : Serializable, Parcelable {
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
  rating: Float,
  smallImage: Int = R.drawable.ic_launcher_background,
  details: RecipeDetails,
  id: String = UUID.randomUUID().toString()
): Recipe? {
  if (title.isEmpty() && time.isEmpty())
    return null

  if (difficulty < 1 || difficulty > 3)
    return null

  if (servings < 1 || servings > 30)
    return null

  if (rating < 0 || rating > 5)
    return null

  return Recipe(title, time, difficulty, servings, rating, smallImage, details, id)
}
