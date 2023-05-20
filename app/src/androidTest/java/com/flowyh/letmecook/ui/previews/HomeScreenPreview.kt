package com.flowyh.letmecook.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.flowyh.letmecook.ui.screens.HomeScreen
import com.flowyh.letmecook.viewmodels.HomeScreenViewModel

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
  val homeScreenViewModel = HomeScreenViewModel()

  HomeScreen(homeScreenViewModel)
}