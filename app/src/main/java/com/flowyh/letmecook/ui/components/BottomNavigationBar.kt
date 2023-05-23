package com.flowyh.letmecook.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.ceil

@Composable
fun BottomNavigationBar(
  modifier: Modifier = Modifier,
  items: List<BottomNavigationBarItem>
) {
  var selectedIndex by remember { mutableStateOf((items.size / 2.0).toInt()) }

  NavigationBar(
    modifier = modifier
  ) {
    items.forEachIndexed { index, item ->
      NavigationBarItem(
        icon = { Icon(item.icon, item.contentDescription) },
        selected = index == selectedIndex,
        onClick = {
          selectedIndex = index
          item.onClick()
        }
      )
    }
  }
}

data class BottomNavigationBarItem(
  val icon: ImageVector,
  val contentDescription: String? = null,
  val onClick: () -> Unit
)

// TODO: extract string resources
fun bottomNavItems(
  onTodayRecipeClick: () -> Unit,
  onShoppingListClick: () -> Unit,
  onHomeClick: () -> Unit,
  onFavoritesClick: () -> Unit,
  onRandomClick: () -> Unit
): List<BottomNavigationBarItem> {
  return listOf(
    BottomNavigationBarItem(
      icon = Icons.Default.Today,
      contentDescription = "today's recipe",
      onClick = { onTodayRecipeClick() }
    ),
    BottomNavigationBarItem(
      icon = Icons.Default.ReceiptLong,
      contentDescription = "shopping list",
      onClick = { onShoppingListClick() }
    ),
    BottomNavigationBarItem(
      icon = Icons.Default.Home,
      contentDescription = "home",
      onClick = { onHomeClick() }
    ),
    BottomNavigationBarItem(
      icon = Icons.Default.Favorite,
      contentDescription = "favorites",
      onClick = { onFavoritesClick() }
    ),
    BottomNavigationBarItem(
      icon = Icons.Default.Casino,
      contentDescription = "random",
      onClick = { onRandomClick() }
    ),
  )
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
  BottomNavigationBar(
    items = bottomNavItems({}, {}, {}, {}, {})
  )
}
