package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import com.flowyh.letmecook.ui.components.SearchBarState

class SearchBarViewModel(
  private val savedStateHandle: SavedStateHandle = SavedStateHandle()
) {
  val searchBarState = savedStateHandle.getStateFlow("searchBarState", SearchBarState.CLOSED)
  val searchTextState = savedStateHandle.getStateFlow("searchTextState", "")

  fun updateSearchBarState(state: SearchBarState) {
    savedStateHandle["searchBarState"] = state
  }

  fun updateSearchTextState(text: String) {
    savedStateHandle["searchTextState"] = text
  }

  fun onSearch() {
    updateSearchBarState(SearchBarState.CLOSED)
    // TODO: search logic
  }
}