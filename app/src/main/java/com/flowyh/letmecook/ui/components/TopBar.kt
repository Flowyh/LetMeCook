package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.flowyh.letmecook.R

// TODO: better onEvent names
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithSearchBar(
  modifier: Modifier = Modifier,
  searchBarState: SearchBarState,
  searchTextState: String,
  scrollBehavior: TopAppBarScrollBehavior,
  onSearchBarTextChange: (String) -> Unit,
  onSearchBarClose: () -> Unit,
  onSearchBarSearch: (String) -> Unit,
  onSearchClick: () -> Unit,
  onProfileClick: () -> Unit
){
  when (searchBarState) {
    SearchBarState.CLOSED -> {
      DefaultTopAppBar(
        scrollBehavior = scrollBehavior,
        onSearchClick = onSearchClick,
        onProfileClick = onProfileClick,
        modifier = modifier
      )
    }
    SearchBarState.OPENED -> {
      TopAppSearchBar(
        text = searchTextState,
        onTextChange = onSearchBarTextChange,
        onClose = onSearchBarClose,
        onSearch = onSearchBarSearch,
        modifier = modifier
      )
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
  modifier: Modifier = Modifier,
  scrollBehavior: TopAppBarScrollBehavior,
  onSearchClick: () -> Unit,
  onProfileClick: () -> Unit
) {
  TopAppBar(
    modifier = modifier,
    title = {
      Text(stringResource(R.string.app_name))
    },
    actions = {
      IconButton(onClick = { onSearchClick() }) {
        Icon(Icons.Default.Search, stringResource(R.string.top_bar_search_content_description))
      }
      IconButton(onClick = { onProfileClick() }) {
        Icon(Icons.Default.Person, stringResource(R.string.top_bar_profile_content_description))
      }
    },
    colors = if (!isSystemInDarkTheme())
                TopAppBarDefaults.topAppBarColors(
                  containerColor = MaterialTheme.colorScheme.primaryContainer,
                  titleContentColor = MaterialTheme.colorScheme.primary,
                  actionIconContentColor = MaterialTheme.colorScheme.primary
                )
             else
                TopAppBarDefaults.topAppBarColors(
                  containerColor = MaterialTheme.colorScheme.primaryContainer,
                  titleContentColor = MaterialTheme.colorScheme.primary,
                  actionIconContentColor = MaterialTheme.colorScheme.primary
                ),
    scrollBehavior = scrollBehavior
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopBar(
  modifier: Modifier = Modifier,
) {
  val scrollBehavior: TopAppBarScrollBehavior =
    TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
  TopAppBar(
    modifier = modifier,
    title = {
      Text(stringResource(R.string.app_name))
    },
    actions = {
      IconButton(onClick = { }) {
        Icon(Icons.Default.ArrowBack, stringResource(R.string.top_bar_search_content_description))
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = Color.Transparent,
      titleContentColor = MaterialTheme.colorScheme.onBackground,
      actionIconContentColor = MaterialTheme.colorScheme.onBackground
    ),
    scrollBehavior = scrollBehavior
  )
}
