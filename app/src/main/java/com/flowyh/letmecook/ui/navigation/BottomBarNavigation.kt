package com.flowyh.letmecook.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.flowyh.letmecook.ui.screens.destinations.FavoriteRecipesScreenDestination
import com.flowyh.letmecook.ui.screens.destinations.RecipeListScreenDestination
import com.flowyh.letmecook.ui.screens.destinations.ShoppingListScreenDestination
import com.flowyh.letmecook.ui.theme.favoritesIcon
import com.flowyh.letmecook.ui.theme.homeIcon
import com.flowyh.letmecook.ui.theme.shoppingListIcon
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarNavigation(
  val direction: DirectionDestinationSpec,
  val icon: ImageVector,
  val contentDescription: String? = null,
) {
  ShoppingListScreen(
    direction = ShoppingListScreenDestination,
    icon = shoppingListIcon,
    contentDescription = "shopping list",
  ),
  RecipeListScreen(
    direction = RecipeListScreenDestination,
    icon = homeIcon,
    contentDescription = "home",
  ),
  FavoritesScreen(
    direction = FavoriteRecipesScreenDestination,
    icon = favoritesIcon,
    contentDescription = "favorites",
  )
}