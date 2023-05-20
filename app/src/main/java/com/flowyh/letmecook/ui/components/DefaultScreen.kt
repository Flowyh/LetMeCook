package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flowyh.letmecook.ui.theme.LetMeCookTheme
import com.flowyh.letmecook.viewmodels.SearchBarViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScreen(
  searchBarViewModel: SearchBarViewModel,
  bottomNavigationBarItems: List<BottomNavigationBarItem>,
  content: @Composable (PaddingValues) -> Unit
) {
  // Important to remember the scroll behavior state
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

  // TODO: Move or remove these callbacks
  fun onProfileClick() {}

  val searchBarState  = searchBarViewModel.searchBarState.collectAsStateWithLifecycle()
  val searchTextState = searchBarViewModel.searchTextState.collectAsStateWithLifecycle()

  LetMeCookTheme {
    Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        Column(Modifier.fillMaxWidth()) {
          TopAppBarWithSearchBar(
            searchBarState = searchBarState.value,
            searchTextState = searchTextState.value,
            scrollBehavior = scrollBehavior,
            onSearchBarTextChange = {
              searchBarViewModel.updateSearchTextState(it)
            },
            onSearchBarClose = {
              searchBarViewModel.updateSearchTextState("")
              searchBarViewModel.updateSearchBarState(SearchBarState.CLOSED)
            },
            onSearchBarSearch = {
              searchBarViewModel.onSearch()
            },
            onSearchClick = {
              searchBarViewModel.updateSearchBarState(SearchBarState.OPENED)
            },
            onProfileClick = { onProfileClick() })
        }
      },
      bottomBar = {
        BottomNavigationBar(
          modifier = Modifier.height(56.dp),
          items = bottomNavigationBarItems
        )
      }
    ) { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
      ) {
        content(innerPadding)
      }
    }
  }
}