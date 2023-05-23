package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.models.FilterType
import com.flowyh.letmecook.models.RecipeFilter
import com.flowyh.letmecook.models.createRecipeFilter
import com.flowyh.letmecook.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipsSection(
  modifier: Modifier = Modifier,
  filters: List<RecipeFilter>,
  selectedFilters: List<RecipeFilter>,
  onFilterSelected: (RecipeFilter) -> Unit
) {
  Surface(
    color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
  ) {
    Row(
      modifier = modifier
        .background(Color.Transparent)
        .horizontalScroll(rememberScrollState()),
      verticalAlignment = Alignment.CenterVertically
    ) {
      filters.forEach { filter ->
        FilterChip(
          modifier = Modifier
            .padding(
              horizontal = MaterialTheme.spacing.small,
              vertical = MaterialTheme.spacing.tiny
            ),
          label = { Text(filter.name) },
          selected = selectedFilters.contains(filter),
          onClick = {
            onFilterSelected(filter)
          }
        )
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ChipsSectionPreview() {
  val filters = listOf(
    createRecipeFilter(FilterType.ALL, "All")!!,
    createRecipeFilter(FilterType.COURSE, "Breakfast")!!,
    createRecipeFilter(FilterType.COURSE, "Lunch")!!,
    createRecipeFilter(FilterType.COURSE, "Dinner")!!,
    createRecipeFilter(FilterType.COURSE, "Snack")!!,
    createRecipeFilter(FilterType.COURSE, "Dessert")!!
  )

  ChipsSection(
    modifier = Modifier.height(32.dp).fillMaxWidth(),
    filters = filters,
    selectedFilters = listOf(filters[0]),
    onFilterSelected = { }
  )
}
