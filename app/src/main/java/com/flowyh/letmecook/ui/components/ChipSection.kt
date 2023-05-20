package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
  onFilterSelected: (Filter) -> Unit
) {
  Surface(
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 3.dp
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
