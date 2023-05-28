package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.flowyh.letmecook.R
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
    textStyle = TextStyle(
      color = MaterialTheme.colorScheme.primary,
    ),
    icon = icon,
    iconModifier = Modifier
      .padding(end = MaterialTheme.spacing.tiny),
    iconContentDescription = contentDescription,
    iconTint = MaterialTheme.colorScheme.primary
  )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun RecipeListItemImage(
  image: String,
  isLeft: Boolean
) {
  GlideImage(
    model = image,
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
  ) {
    it.error(R.drawable.ic_launcher_foreground)
      .placeholder(R.drawable.ic_launcher_background)
      .load(image)
  }
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
          contentDescription = stringResource(R.string.recipe_title_icon_content_description)
        )
        RecipeListItemTextIcon(
          text = recipe.time,
          icon = cookingTimeIcon,
          contentDescription = stringResource(R.string.cooking_time_icon_content_description)
        )
        RecipeListItemTextIcon(
          text= "Difficulty: ${recipe.difficulty}",
          icon = difficultyIcon,
          contentDescription = stringResource(R.string.difficulty_icon_content_description)
        )
        RecipeListItemTextIcon(
          text= "Servings: ${recipe.servings}",
          icon = servingsIcon,
          contentDescription = stringResource(R.string.servings_icon_content_description)
        )
      }
      if (!imageOnLeft)
        RecipeListItemImage(recipe.smallImage, false)
    }
  }
}

@Composable
fun FavoriteRecipeListItem(
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
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(
          modifier = Modifier
            .padding(
              horizontal = MaterialTheme.spacing.small
            ),
          text = recipe.title,
          style = TextStyle (
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
          ),
        )
        RatingBar(
          modifier = Modifier
            .fillMaxHeight(0.2f)
            .padding(
              horizontal = MaterialTheme.spacing.small
            ),
          rating = recipe.rating,
          onRatingChanged = {},
        )
      }
      if (!imageOnLeft)
        RecipeListItemImage(recipe.smallImage, false)
    }
  }
}

@Preview(showBackground = true)
@Composable
fun FavoriteRecipeListItemPreview() {
  FavoriteRecipeListItem(
    recipe = createRecipe(
      title = "Recipe titleeeeeeeeeee",
      time = "30 min",
      difficulty = 3,
      servings = 3,
      rating = 4.5f,
      smallImage = "",
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
        filters = listOf(),
        bigImage = ""
      )!!
    )!!,
    onRecipeClick = {}
  )
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
      rating = 4.5f,
      smallImage = "",
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
        filters = listOf(),
        bigImage = ""
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
      rating = 4.5f,
      smallImage = "",
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
        filters = listOf(),
        bigImage = ""
      )!!
    )!!,
    onRecipeClick = {},
    imageOnLeft = false
  )
}
