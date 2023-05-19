package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.ui.theme.spacing

data class Filter(
  val name: String,
  val selected: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipsSection(
  modifier: Modifier = Modifier,
  filters: List<Filter>,
  selectedFilters: List<Filter>,
  onFilterSelected: (Filter) -> Unit,
) {
  Row(
    modifier = modifier.horizontalScroll(rememberScrollState()),
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
