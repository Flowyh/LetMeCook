package com.flowyh.letmecook.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.flowyh.letmecook.models.*
import com.flowyh.letmecook.ui.components.*
import com.flowyh.letmecook.ui.theme.cookingTimeIcon
import com.flowyh.letmecook.ui.theme.difficultyIcon
import com.flowyh.letmecook.ui.theme.servingsIcon
import com.flowyh.letmecook.ui.theme.spacing
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import java.util.*

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
  recipe: Recipe,
  navController: NavController,
  resultNavigator: ResultBackNavigator<Unit>
) {
  var rating by remember { mutableStateOf(recipe.rating) }

  DefaultScreenWithoutSearchbar(
    navController = navController,
    onNavigationClick = {
      resultNavigator.navigateBack()
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(
          horizontal = MaterialTheme.spacing.small,
          vertical = MaterialTheme.spacing.small
        )
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
      // Filters
      RecipeScreenFilters(recipeFilters = recipe.details.filters)
      // Spacer with primary color
      RecipeScreenSpacer()
      // Title
      RecipeScreenTitle(
        modifier = Modifier.fillMaxHeight(),
        title = recipe.title
      )
      // Rating
      RecipeScreenRating(rating = rating)
      // Big image
      RecipeScreenImage(
        image = recipe.details.bigImage,
        contentDescription = "${recipe.title} image"
      )

      // Quick details
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = MaterialTheme.spacing.tiny),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        RecipeScreenQuickDetail(
          modifier = Modifier
            .fillMaxHeight(0.1f),
          text = "Time: ${recipe.time}",
          icon = cookingTimeIcon,
          contentDescription = "cooking time"
        )
        RecipeScreenQuickDetail(
          modifier = Modifier
            .fillMaxHeight(0.1f),
          text = "Servings: ${recipe.servings}",
          icon = servingsIcon,
          contentDescription = "servings"
        )
        RecipeScreenQuickDetail(
          modifier = Modifier
            .fillMaxHeight(0.1f),
          text = "Difficulty: ${recipe.difficulty}/3",
          icon = difficultyIcon,
          contentDescription = "difficulty"
        )
      }
      // Spacer with primary color
      RecipeScreenSpacer()
      // Description
      RecipeScreenDescription(description = recipe.details.description)
      // Ingredients
      RecipeScreenIngredients(
        ingredients = recipe.details.ingredients,
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = MaterialTheme.spacing.small)
      )
      // Instructions
      RecipeScreenInstructions(
        instructions = recipe.details.steps,
        modifier = Modifier
          .fillMaxWidth()
      )
      // Spacer with primary color
      RecipeScreenSpacer()
      // Rating
      RecipeScreenRateIt(
        rating = recipe.rating
      ) { newRating ->
        rating = newRating
      }
    }
  }
}

@Composable
fun RecipeFilterTypeList(
  modifier: Modifier = Modifier,
  filterType: FilterType,
  filters: List<RecipeFilter>
) {
  val filtersStringSeparator: String = when (filterType) {
    FilterType.ALL -> " • "
    FilterType.CUISINE -> " • "
    FilterType.DIET -> " • "
    FilterType.COURSE -> " • "
  }

  val filterTypeString = filterType.name
                          .lowercase()
                          .replaceFirstChar {
                            if (it.isLowerCase())
                              it.titlecase(Locale.getDefault())
                            else it.toString()
                          } + " > "

  val filtersString = filters.joinToString(separator = filtersStringSeparator) { filter ->
    filter.name
      .lowercase(Locale.getDefault())
      .replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
      }
  }

  Row(
    modifier = modifier
      .wrapContentHeight()
      .horizontalScroll(rememberScrollState()),
  ) {
    Box(
      modifier = Modifier
        .weight(0.2f),
      contentAlignment = Alignment.CenterEnd
    ) {
      Text(
        text = filterTypeString,
        style = TextStyle(
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Bold,
          fontSize = MaterialTheme.typography.bodySmall.fontSize
        ),
        modifier = Modifier
          .fillMaxWidth()
      )
    }
    Box(
      modifier = Modifier
        .weight(0.8f),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = filtersString,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier
          .fillMaxWidth()
      )
    }
  }
}

@Composable
fun RecipeScreenFilters(recipeFilters: List<RecipeFilter>) {
  val filterTypeToRecipeFilters = FilterType.values().associateWith {
    recipeFilters.filter { filter ->
      filter.type == it
    }
  }

  Column {
    for (filterType in FilterType.values()) {
      val filters = filterTypeToRecipeFilters[filterType] ?: emptyList()
      if (filters.isNotEmpty()) {
        RecipeFilterTypeList(
          modifier = Modifier.fillMaxWidth(),
          filterType = filterType,
          filters = filters,
        )
      }
    }
  }
}

@Composable
fun RecipeScreenSpacer() {
  Spacer(
    modifier = Modifier
      .fillMaxWidth()
      .height(2.dp)
      .clip(MaterialTheme.shapes.small)
      .background(MaterialTheme.colorScheme.primary)
  )
}


@Composable
fun RecipeScreenTitle(
  modifier: Modifier = Modifier,
  title: String,
  color: Color = MaterialTheme.colorScheme.primary,
  fontTypography: TextStyle = MaterialTheme.typography.headlineLarge,
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = title,
      style = TextStyle(
        color = color,
        fontSize = fontTypography.fontSize,
        fontWeight = FontWeight.Bold,
      ),
      modifier = Modifier
    )
  }
}

@Composable
fun RecipeScreenRating(
  rating: Float
) {
  Row(
    modifier = Modifier
      .height(24.dp)
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny)
  ) {
    RatingBar(
      rating = rating,
      onRatingChanged = {},
    )
    Box(
      modifier = Modifier.fillMaxHeight(),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = "(${rating} / 5)",
        style = TextStyle(
          color = MaterialTheme.colorScheme.primary,
          fontSize = MaterialTheme.typography.bodyMedium.fontSize,
          fontWeight = FontWeight.Bold,
        ),
      )
    }
  }
}

@Composable
fun RecipeScreenImage(
  image: Int,
  contentDescription: String
) {
  Image(
    painter = painterResource(id = image),
    contentDescription = contentDescription,
    contentScale = ContentScale.Crop,
    modifier = Modifier
      .fillMaxWidth()
      .fillMaxHeight(0.5f)
  )
}

@Composable
fun RecipeScreenDescription(
  description: String
) {
  Box(
    modifier = Modifier
      .fillMaxWidth(),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = description,
      textAlign = TextAlign.Justify,
      style = TextStyle(
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        fontWeight = FontWeight.Normal,
      ),
      modifier = Modifier
        .fillMaxWidth()
    )
  }
}

@Composable
fun RecipeScreenQuickDetail(
  modifier: Modifier = Modifier,
  text: String,
  icon: ImageVector,
  contentDescription: String
) {
  TextWithIcon(
    modifier = modifier,
    text = text,
    textStyle = TextStyle(fontWeight = FontWeight.Bold),
    textModifier = Modifier,
    icon = icon,
    iconModifier = Modifier
      .padding(end = MaterialTheme.spacing.tiny),
    iconContentDescription = contentDescription,
    iconTint = MaterialTheme.colorScheme.primary,
  )
}

@Composable
fun RecipeScreenIngredients(
  modifier: Modifier = Modifier,
  ingredients: List<RecipeIngredient>,
) {
  Card(
    modifier = modifier,
    shape = MaterialTheme.shapes.medium,
    elevation = CardDefaults.cardElevation(4.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(MaterialTheme.spacing.small),
      verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny)
    ) {
      RecipeScreenTitle(
        modifier = Modifier.fillMaxWidth(),
        title = "Ingredients",
        fontTypography = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface,
      )
      for (ingredient in ingredients) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          ClickableIconWithText(
            text = ingredient.name,
            textStyle = MaterialTheme.typography.bodyLarge,
            inactiveIcon = Icons.Default.CheckBoxOutlineBlank,
            activeIcon = Icons.Default.CheckBox,
            iconContentDescription = "Ingredient",
            iconTint = MaterialTheme.colorScheme.primary,
            iconModifier = Modifier
              .padding(end = MaterialTheme.spacing.tiny)
          )
          Box(
            modifier = Modifier.fillMaxHeight(),
          ) {
            Text(
              text = "${ingredient.quantity} ${ingredient.unit}",
              style = MaterialTheme.typography.bodyLarge,
            )
          }
        }
      }
    }
  }
}

@Composable
fun RecipeScreenInstructions(
  modifier: Modifier,
  instructions: List<String>
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny)
  ) {
    RecipeScreenTitle(
      title = "Instructions",
      fontTypography = MaterialTheme.typography.headlineMedium,
      color = MaterialTheme.colorScheme.onSurface,
    )
    for ((index, instruction) in instructions.withIndex()) {
      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.tiny)
      ) {
        Text(
          text = "${index + 1}.",
          style = TextStyle(
            color = MaterialTheme.colorScheme.primary,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = FontWeight.Bold,
          ),
        )
        Text(
          text = instruction,
          style = MaterialTheme.typography.bodyLarge,
        )
      }
    }
  }
}


@Composable
fun RecipeScreenRateIt(
  modifier: Modifier = Modifier,
  rating: Float,
  onRatingChanged: (Float) -> Unit,
) {
  var localRating by remember { mutableStateOf(rating) }

  Row(
    modifier = modifier
      .fillMaxHeight(0.1f)
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.Center
  ) {
    RatingBar(
      modifier = Modifier,
      rating = localRating,
      onRatingChanged = {
        onRatingChanged(it)
        localRating = it
      }
    )
  }
}
