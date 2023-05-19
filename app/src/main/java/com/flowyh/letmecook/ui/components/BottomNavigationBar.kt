package com.flowyh.letmecook.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun BottomNavigationBar(
  items: List<BottomNavigationBarItem>,
  modifier: Modifier = Modifier,
) {
  var selectedIndex by remember { mutableStateOf(0) }

  NavigationBar(
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.surface,
    contentColor = MaterialTheme.colorScheme.onSurface,
    tonalElevation = 4.dp,
  ) {
    items.forEachIndexed { index, item ->
      NavigationBarItem(
        icon = { Icon(item.icon, item.contentDescription) },
        label = { Text(item.label, fontSize = 9.sp) },
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
  val label: String,
  val onClick: () -> Unit
)

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
      label = "Today's recipe",
      onClick = { onTodayRecipeClick() }
    ),
    BottomNavigationBarItem(
      icon = Icons.Default.ReceiptLong,
      label = "Shopping list",
      onClick = { onShoppingListClick() }
    ),
    BottomNavigationBarItem(
      icon = Icons.Default.Home,
      label = "Home",
      onClick = { onHomeClick() }
    ),
    BottomNavigationBarItem(
      icon = Icons.Default.Favorite,
      label = "Favorites",
      onClick = { onFavoritesClick() }
    ),
    BottomNavigationBarItem(
      icon = Icons.Default.Casino,
      label = "Random",
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
