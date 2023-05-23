package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.models.*
import com.flowyh.letmecook.ui.theme.*

@Composable
fun RecipeListItemTextIcon(
  text: String,
  icon: ImageVector,
  contentDescription: String
) {
  TextWithIcon(
    modifier = Modifier
      .fillMaxWidth()
      .padding(
        horizontal = MaterialTheme.spacing.small
      ),
    text = text,
    textModifier = Modifier.fillMaxWidth(),
    icon = icon,
    iconModifier = Modifier
      .padding(end = MaterialTheme.spacing.tiny),
    iconContentDescription = contentDescription,
  )
}

@Composable
fun RecipeListItemImage(
  image: Int,
  isLeft: Boolean
) {
  Image(
    painterResource(image),
    contentDescription = null,
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .fillMaxWidth(if (isLeft) 0.5f else 1f)
      .fillMaxHeight()
      .padding(
        start = if (isLeft)
                  MaterialTheme.spacing.tiny
                else
                  0.dp,
        end = if (isLeft)
                0.dp
              else
                MaterialTheme.spacing.tiny,
        top = MaterialTheme.spacing.tiny,
        bottom = MaterialTheme.spacing.tiny
      )
      .shadow(
        elevation = 2.dp,
        shape = MaterialTheme.shapes.medium,
        clip = true
      )
  )
}

@Composable
fun RecipeListItem(
  modifier: Modifier = Modifier,
  recipe: Recipe,
  onRecipeClick: (Recipe) -> Unit,
  imageOnLeft: Boolean = true
) {
  Card(
    modifier = modifier,
    shape = MaterialTheme.shapes.medium,
    elevation = CardDefaults.cardElevation(4.dp),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .clickable { onRecipeClick(recipe) }
    ) {
      if (imageOnLeft)
        RecipeListItemImage(recipe.smallImage, true)

      Column(
        modifier = Modifier
          .fillMaxWidth(if (imageOnLeft) 1f else 0.5f)
          .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
      ) {
        RecipeListItemTextIcon(
          text = recipe.title,
          icon = recipeTitleIcon,
          contentDescription = "recipe title"
        )
        RecipeListItemTextIcon(
          text = recipe.time,
          icon = cookingTimeIcon,
          contentDescription = "cooking time"
        )
        RecipeListItemTextIcon(
          text= "Difficulty: ${recipe.difficulty}",
          icon = difficultyIcon,
          contentDescription = "difficulty"
        )
        RecipeListItemTextIcon(
          text= "Servings: ${recipe.servings}",
          icon = servingsIcon,
          contentDescription = "servings"
        )
      }
      if (!imageOnLeft)
        RecipeListItemImage(recipe.smallImage, false)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreview() {
  RecipeListItem(
    recipe = createRecipe(
      title = "Recipe title",
      time = "30 min",
      difficulty = 3,
      servings = 3,
      details = createRecipeDetails(
        description = "Recipe",
        ingredients = listOf(
          createRecipeIngredient(
          name = "Ingredient",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.OTHER
        )!!),
        steps = listOf("Step 1", "Step 2", "Step 3"),
        rating = 4.5f,
        filters = listOf()
      )!!
    )!!,
    onRecipeClick = {}
  )
}

@Preview(showBackground = true)
@Composable
fun RecipeListItemPreviewRight() {
  RecipeListItem(
    recipe = createRecipe(
      title = "Recipe title",
      time = "30 min",
      difficulty = 3,
      servings = 3,
      details = createRecipeDetails(
        description = "Recipe",
        ingredients = listOf(
          createRecipeIngredient(
          name = "Ingredient",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.OTHER
        )!!),
        steps = listOf("Step 1", "Step 2", "Step 3"),
        rating = 4.5f,
        filters = listOf()
      )!!
    )!!,
    onRecipeClick = {},
    imageOnLeft = false
  )
}
