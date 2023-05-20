package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.flowyh.letmecook.R

enum class SearchBarState {
  CLOSED,
  OPENED
}

@Composable
fun SearchBar(
  modifier: Modifier = Modifier,
  text: String,
  onTextChange: (String) -> Unit,
  onClose: () -> Unit,
  onSearch: (String) -> Unit,
) {
  Surface(
    modifier = modifier,
  ) {
    TextField(
      modifier = Modifier
        .fillMaxWidth(),
      value = text,
      onValueChange = { onTextChange(it) },
      placeholder = { Text(
        modifier = Modifier.alpha(0.9f),
        text = stringResource(R.string.top_bar_search_hint),
      ) },
      textStyle = TextStyle(
        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
      ),
      singleLine = true,
      leadingIcon = {
        IconButton(
          modifier = Modifier.alpha(0.9f),
          onClick = {
            onSearch(text)
          }
        ) {
          Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(R.string.top_bar_search_content_description)
          )
        }
      },
      trailingIcon = {
        IconButton(
          onClick = {
            if (text.isNotEmpty())
              onTextChange("")
            else
              onClose()
          }
        ) {
          Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.top_app_bar_close_content_description)
          )
        }
      },
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Search
      ),
      keyboardActions = KeyboardActions(
        onSearch = { onSearch(text) }
      )
    )
  }
}

@Preview
@Composable
fun SearchBarPreview() {
  SearchBar(
    text = "Test!",
    onTextChange = {},
    onClose = {},
    onSearch = {}
  )
}