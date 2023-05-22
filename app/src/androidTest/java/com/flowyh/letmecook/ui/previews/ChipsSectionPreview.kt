package com.flowyh.letmecook.ui.previews

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.models.FilterType
import com.flowyh.letmecook.models.RecipeFilter
import com.flowyh.letmecook.models.createRecipeFilter
import com.flowyh.letmecook.ui.components.ChipsSection

@Preview(showBackground = true)
@Composable
fun ChipsSectionPreview() {
  ChipsSection(
    modifier = Modifier.height(32.dp).fillMaxWidth(),
    filters = listOf(
      createRecipeFilter(FilterType.ALL, "All")!!,
      createRecipeFilter(FilterType.COURSE, "Breakfast")!!,
      createRecipeFilter(FilterType.COURSE, "Lunch")!!,
      createRecipeFilter(FilterType.COURSE, "Dinner")!!,
      createRecipeFilter(FilterType.COURSE, "Snack")!!,
      createRecipeFilter(FilterType.COURSE, "Dessert")!!
    ),
    selectedFilters = listOf(createRecipeFilter(FilterType.COURSE, "All")!!),
    onFilterSelected = { }
  )
}
