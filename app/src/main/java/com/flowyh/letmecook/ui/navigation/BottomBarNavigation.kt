package com.flowyh.letmecook.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.ui.graphics.vector.ImageVector
import com.flowyh.letmecook.ui.screens.destinations.FavoriteRecipesScreenDestination
import com.flowyh.letmecook.ui.screens.destinations.RecipeListScreenDestination
import com.flowyh.letmecook.ui.screens.destinations.ShoppingListScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarNavigation(
  val direction: DirectionDestinationSpec,
  val icon: ImageVector,
  val contentDescription: String? = null,
) {
  ShoppingListScreen( // TODO: change direction
    direction = ShoppingListScreenDestination,
    icon = Icons.Default.ReceiptLong,
    contentDescription = "shopping list",
  ),
  RecipeListScreen(
    direction = RecipeListScreenDestination,
    icon = Icons.Default.Home,
    contentDescription = "home",
  ),
  FavoritesScreen( // TODO: change direction
    direction = FavoriteRecipesScreenDestination,
    icon = Icons.Default.Favorite,
    contentDescription = "favorites",
  )
}