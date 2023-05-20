package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.ui.theme.spacing

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
      .size(32.dp)
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
          icon = Icons.Default.MenuBook,
          contentDescription = "recipe title"
        )
        RecipeListItemTextIcon(
          text = recipe.time,
          icon = Icons.Default.Timer,
          contentDescription = "time to make"
        )
        RecipeListItemTextIcon(
          text= "Difficulty: ${recipe.difficulty}",
          icon = Icons.Default.Leaderboard,
          contentDescription = "difficulty"
        )
        RecipeListItemTextIcon(
          text= "Servings: ${recipe.servings}",
          icon = Icons.Default.Groups,
          contentDescription = "servings"
        )
      }
      if (!imageOnLeft)
        RecipeListItemImage(recipe.smallImage, false)
    }
  }
}
