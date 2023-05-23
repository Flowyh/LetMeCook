package com.flowyh.letmecook.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flowyh.letmecook.R
import com.flowyh.letmecook.ui.theme.LetMeCookTheme
import com.flowyh.letmecook.ui.theme.goBackIcon
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

  val searchBarState  = searchBarViewModel.searchBarState.collectAsStateWithLifecycle()
  val searchTextState = searchBarViewModel.searchTextState.collectAsStateWithLifecycle()

  LetMeCookTheme {
    Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        Box(
          Modifier
            .semantics { isContainer = true }
            .zIndex(2f)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
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
              onSearchBarTextChange = { text ->
                searchBarViewModel.updateSearchTextState(text)
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
              }
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
      content(innerPadding)
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScreenWithoutSearchbarWithNavigation(
  bottomNavigationBarItems: List<BottomNavigationBarItem>,
  scrollBehavior: TopAppBarScrollBehavior =
    TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
  navigationIcon: ImageVector = goBackIcon,
  navigationIconContentDescription: String = stringResource(R.string.top_app_bar_go_back_content_description),
  onNavigationClick: () -> Unit,
  content: @Composable (PaddingValues) -> Unit
) {
  LetMeCookTheme {
    Scaffold(
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      topBar = {
        Box(
          Modifier
            .semantics { isContainer = true }
            .zIndex(2f)
            .fillMaxWidth()
        ) {
          TopBarWithoutSearchbarWithNavigation(
            scrollBehavior = scrollBehavior,
            navigationIcon = navigationIcon,
            navigationIconContentDescription = navigationIconContentDescription,
            onNavigationClick = {
              onNavigationClick()
            }
          )
        }
      },
      bottomBar = {
        BottomNavigationBar(
          modifier = Modifier.height(56.dp),
          items = bottomNavigationBarItems
        )
      }
    ) { innerPadding ->
      content(innerPadding)
    }
  }
}