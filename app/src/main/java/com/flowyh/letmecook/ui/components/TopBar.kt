package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.flowyh.letmecook.R
import com.flowyh.letmecook.ui.theme.LetMeCookTheme
import com.flowyh.letmecook.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSample(
  onProfileClick: () -> Unit,
  onSearchClick: () -> Unit,
  scrollBehavior: TopAppBarScrollBehavior,
  modifier: Modifier = Modifier
){
  TopAppBar(
    modifier = modifier.background(MaterialTheme.colorScheme.primary),
    title = {
      Text(stringResource(R.string.application_name))
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primary,
      scrolledContainerColor = MaterialTheme.colorScheme.secondary,
      navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
      titleContentColor = MaterialTheme.colorScheme.onPrimary,
      actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    ),
    navigationIcon = {
      Icon(
        Icons.Default.RestaurantMenu,
        null,
        modifier = Modifier.padding(start = MaterialTheme.spacing.small)
      )
    },
    actions = {
      IconButton(onClick = { onSearchClick() }) {
        Icon(Icons.Default.Search, stringResource(R.string.top_bar_search_content_description))
      }
      IconButton(onClick = { onProfileClick() }) {
        Icon(Icons.Default.Person, stringResource(R.string.top_bar_profile_content_description))
      }
    },
    scrollBehavior = scrollBehavior
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TopAppBarSamplePreview() {
  // Important to remember the scroll behavior state
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

  LetMeCookTheme {
    Scaffold(
      // Needs to be nested scroll to work with the scroll behavior
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        Column(
          Modifier.fillMaxWidth()
        ) {
          TopAppBarSample({}, {}, scrollBehavior)
        }
      },
    ) { innerPadding ->
      LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
      ) {
        items(120) { item ->
          Text(
            text = "Item $item",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
          )
        }
      }
    }
  }
}