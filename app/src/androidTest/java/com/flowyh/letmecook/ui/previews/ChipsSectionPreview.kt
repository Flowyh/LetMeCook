package com.flowyh.letmecook.ui.previews

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.ui.components.ChipsSection
import com.flowyh.letmecook.ui.components.Filter

@Preview(showBackground = true)
@Composable
fun ChipsSectionPreview() {
  ChipsSection(
    modifier = Modifier.height(32.dp).fillMaxWidth(),
    filters = listOf(
      Filter("All", true),
      Filter("Breakfast", false),
      Filter("Lunch", false),
      Filter("Dinner", false),
      Filter("Snack", false),
      Filter("Dessert", false),
    ),
    selectedFilters = listOf(Filter("All", true)),
    onFilterSelected = { }
  )
}
