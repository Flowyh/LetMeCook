package com.flowyh.letmecook.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.flowyh.letmecook.ui.components.BottomNavigationBar
import com.flowyh.letmecook.ui.components.bottomNavItems

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
  BottomNavigationBar(
    items = bottomNavItems({}, {}, {}, {}, {})
  )
}
