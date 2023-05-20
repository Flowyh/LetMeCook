package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.models.RecipeDetails
import com.flowyh.letmecook.ui.theme.spacing

@Composable
fun RecipeListWithFilterChips(
  modifier: Modifier = Modifier,
  scaffoldPadding: PaddingValues,
  lazyListState: LazyListState,
  recipes: List<Recipe>,
  onRecipeClick: (Recipe) -> Unit,
  recipeFilters: List<Filter>,
  selectedFilters: List<Filter>,
  onFilterClick: () -> Unit,
) {
  Column(
    modifier = modifier.padding(scaffoldPadding)
  ) {
    ChipsSection(
      modifier = Modifier
        .height(32.dp)
        .fillMaxWidth(),
      filters = recipeFilters,
      selectedFilters = selectedFilters,
      onFilterSelected = { onFilterClick() }
    )
    LazyColumn(
      modifier = Modifier
        .fillMaxSize(),
      state = lazyListState
    ) {
      items(recipes.size) { item ->
        RecipeListItem(recipe = recipes[item], onRecipeClick = { onRecipeClick(it) })
      }
    }
  }
}

@Composable
fun RecipeListItem(
  modifier: Modifier = Modifier,
  recipe: Recipe,
  onRecipeClick: (Recipe) -> Unit,
  imageOnLeft: Boolean = true
) {
  val halfOfScreen: Int = LocalConfiguration.current.screenWidthDp / 2

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
        Image(
          painterResource(recipe.smallImage),
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxWidth(0.5f)
            .fillMaxHeight()
            .shadow(
              elevation = 2.dp,
              shape = MaterialTheme.shapes.large,
              clip = true
            )
            .padding(MaterialTheme.spacing.tiny)
            .clip(MaterialTheme.shapes.medium)
        )
      Column(
        modifier = Modifier
          .fillMaxWidth(if (imageOnLeft) 1f else 0.5f)
          .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
      ) {
        TextWithIcon(
          modifier = Modifier
            .fillMaxWidth()
            .padding(
              horizontal = MaterialTheme.spacing.small
            ),
          text = recipe.title,
          textModifier = Modifier.fillMaxWidth(),
          icon = Icons.Default.MenuBook,
          iconModifier = Modifier.size(32.dp).padding(end = MaterialTheme.spacing.tiny),
          iconDescription = "recipe title",
        )
        TextWithIcon(
          modifier = Modifier
            .fillMaxWidth()
            .padding(
              horizontal = MaterialTheme.spacing.small
            ),
          text = recipe.time,
          textModifier = Modifier.fillMaxWidth(),
          icon = Icons.Default.Timer,
          iconModifier = Modifier.size(32.dp).padding(end = MaterialTheme.spacing.tiny),
          iconDescription = "time to make",
        )
        TextWithIcon(
          modifier = Modifier
            .fillMaxWidth()
            .padding(
              horizontal = MaterialTheme.spacing.small
            ),
          text = "Difficulty: ${recipe.difficulty}",
          textModifier = Modifier.fillMaxWidth(),
          icon = Icons.Default.Leaderboard,
          iconModifier = Modifier.size(32.dp).padding(end = MaterialTheme.spacing.tiny),
          iconDescription = "difficulty",
        )
        TextWithIcon(
          modifier = Modifier
            .fillMaxWidth()
            .padding(
              horizontal = MaterialTheme.spacing.small
            ),
          text = "Servings: ${recipe.servings}",
          textModifier = Modifier.fillMaxWidth(),
          icon = Icons.Default.Groups,
          iconModifier = Modifier.size(32.dp).padding(end = MaterialTheme.spacing.tiny),
          iconDescription = "servings",
        )
      }
      if (!imageOnLeft)
        Image(
          painterResource(recipe.smallImage),
          contentDescription = null,
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .shadow(
              elevation = 2.dp,
              shape = MaterialTheme.shapes.large,
              clip = true
            )
            .padding(MaterialTheme.spacing.tiny)
            .clip(MaterialTheme.shapes.medium)
        )
    }
  }
}
