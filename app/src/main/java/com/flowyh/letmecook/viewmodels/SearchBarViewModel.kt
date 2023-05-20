package com.flowyh.letmecook.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import com.flowyh.letmecook.models.Recipe
import com.flowyh.letmecook.ui.components.SearchBarState
import kotlinx.coroutines.flow.StateFlow

class SearchBarViewModel(
  private val savedStateHandle: SavedStateHandle,
  private val recipeList: StateFlow<List<Recipe>> // TODO: Should it be a StateFlow?
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
    Log.d("SearchBarViewModel", "onSearch: ${searchTextState.value}")
    // TODO: search logic
  }
}