package com.flowyh.letmecook.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.flowyh.letmecook.controllers.repositories.FirestoreRepositoryImpl
import com.flowyh.letmecook.models.IngredientType
import com.flowyh.letmecook.models.RecipeIngredient
import com.flowyh.letmecook.models.ShoppingList
import com.flowyh.letmecook.ui.components.DefaultScreenWithoutSearchbar
import com.flowyh.letmecook.ui.components.FavoriteRecipeListItem
import com.flowyh.letmecook.ui.screens.destinations.RecipeScreenDestination
import com.flowyh.letmecook.ui.theme.*
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ShoppingListScreen(
//  viewModel: MainBundledViewModel, // TODO: uncomment when view model is ready
  navController: NavController,
  resultNavigator: ResultBackNavigator<Unit>
) {
  // UI state
  val scope = rememberCoroutineScope()
  val snackBarHostState = remember { SnackbarHostState() }
  val listState = rememberLazyListState()

  // TODO: replace with viewModel.shoppingLists
  val shoppingLists = listOf(
    ShoppingList(
      id = 1,
      ingredients = listOf(
        RecipeIngredient(
          name = "Ingredient 1",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        ),
        RecipeIngredient(
          name = "Ingredient 2",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        ),
        RecipeIngredient(
          name = "Ingredient 3",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        ),
        RecipeIngredient(
          name = "Ingredient 4",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        ),
        RecipeIngredient(
          name = "Ingredient 5",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        )
      ),
      alreadyBoughtIngredients = listOf(
        1,
        3
      ),
      thumbnail = 0,
      recipeTitle = "Title 1",
      dateTime = LocalDateTime.now()
    ),
    ShoppingList(
      id = 2,
      ingredients = listOf(
        RecipeIngredient(
          name = "Ingredient 1",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        ),
        RecipeIngredient(
          name = "Ingredient 2",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        ),
        RecipeIngredient(
          name = "Ingredient 3",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        ),
        RecipeIngredient(
          name = "Ingredient 4",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        ),
        RecipeIngredient(
          name = "Ingredient 5",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.VEGETABLE
        )
      ),
      alreadyBoughtIngredients = listOf(
        1,
        3
      ),
      thumbnail = 0,
      recipeTitle = "Title 2",
      dateTime = LocalDateTime.now()
    ),
  )

  DefaultScreenWithoutSearchbar(
    navController = navController,
    onNavigationClick = {
      resultNavigator.navigateBack()
    },
    snackBarHostState = snackBarHostState
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(
            horizontal = MaterialTheme.spacing.medium,
            vertical = MaterialTheme.spacing.small
          ),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
      ) {
        items(shoppingLists.size) { item ->
          RecipeScreenIngredients(
            modifier = Modifier,
            cardTitle = shoppingLists[item].recipeTitle,
            cardTitleTypography = MaterialTheme.typography.headlineSmall,
            cardTopLeadingContent = { modifier ->
              // Jump to recipe icon
              IconButton(
                onClick = {
                  // TODO: jump to recipe based on shopping list id (getRecipeById in viewModel?)
                },
                modifier = modifier
                  .padding(end = MaterialTheme.spacing.small)
                  .size(24.dp)
              ) {
                Icon(
                  imageVector = launchIcon,
                  contentDescription = "go to recipe",
                  tint = MaterialTheme.colorScheme.primary
                )
              }
            },
            cardTopTrailingContent = { modifier ->
              // Delete shopping list icon
              IconButton(
                onClick = {
                  // TODO: remove shopping list in room
                },
                modifier = modifier
                  .padding(end = MaterialTheme.spacing.small)
                  .size(24.dp)
              ) {
                Icon(
                  imageVector = deleteIcon,
                  contentDescription = "delete shopping list",
                  tint = MaterialTheme.colorScheme.primary
                )
              }
            },
            ingredients = shoppingLists[item].ingredients,
          ) { ingredientState -> // On checkbox click
            // TODO: change list ingredient state based on boolean value (use viewmodel)
          }
        }
      }
    }
  }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun ShoppingListScreenPreview() {
  ShoppingListScreen(
    navController = rememberAnimatedNavController(),
    resultNavigator = EmptyResultBackNavigator()
  )
}