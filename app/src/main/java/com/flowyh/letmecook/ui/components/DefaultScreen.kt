package com.flowyh.letmecook.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flowyh.letmecook.ui.theme.LetMeCookTheme
import com.flowyh.letmecook.viewmodels.SearchBarViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DefaultScreen(
  searchBarViewModel: SearchBarViewModel,
  bottomNavigationBarItems: List<BottomNavigationBarItem>,
  scrollBehavior: TopAppBarScrollBehavior =
    TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
  content: @Composable (PaddingValues) -> Unit
) {
  // TODO: Move or remove these callbacks
  fun onProfileClick() {}

  val searchBarState  = searchBarViewModel.searchBarState.collectAsStateWithLifecycle()
  val searchTextState = searchBarViewModel.searchTextState.collectAsStateWithLifecycle()

  LetMeCookTheme {
    Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        Box(Modifier.semantics { isContainer = true }.zIndex(2f).fillMaxWidth()) {
          AnimatedContent(
            targetState = searchBarState.value,
            transitionSpec = {
              if (targetState == SearchBarState.OPENED && initialState == SearchBarState.CLOSED) {
                slideInHorizontally { width -> width } + fadeIn() with
                        slideOutHorizontally { width -> -width } + fadeOut()
              } else {
                slideInHorizontally { width -> -width } + fadeIn() with
                        slideOutHorizontally { width -> width } + fadeOut()
              }.using(
                SizeTransform(clip = false)
              )
            }
          ) {
            TopAppBarWithSearchBar(
              searchBarState = it,
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
              onProfileClick = { onProfileClick() }
            )
          }
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