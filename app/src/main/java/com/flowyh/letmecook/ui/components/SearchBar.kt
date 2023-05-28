package com.flowyh.letmecook.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.flowyh.letmecook.R

enum class SearchBarState {
  CLOSED,
  OPENED
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopAppSearchBar(
  modifier: Modifier = Modifier,
  text: String,
  onTextChange: (String) -> Unit,
  onClose: () -> Unit,
  onSearch: (String) -> Unit,
) {
  val textFieldFocusRequester = LocalFocusManager.current

  val keyboardController = LocalSoftwareKeyboardController.current

  val onSearchClicked = {
    keyboardController?.hide()
    textFieldFocusRequester.clearFocus()
    onSearch(text)
  }

  Surface(
    modifier = modifier,
  ) {
    TextField(
      modifier = Modifier
        .fillMaxWidth(),
      colors = TextFieldDefaults.colors(
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
        focusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        cursorColor = Color.Transparent,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
        focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
      ),
      value = text,
      onValueChange = { onTextChange(it) },
      placeholder = { Text(
        modifier = Modifier.alpha(0.9f),
        text = stringResource(R.string.top_bar_search_hint),
      ) },
      textStyle = TextStyle(
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
      ),
      singleLine = true,
      leadingIcon = {
        IconButton(
          modifier = Modifier.alpha(0.9f),
          onClick = {
            onSearchClicked()
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
        onSearch = {
          onSearchClicked()
        }
      ),
      shape = RectangleShape,
    )
  }
}

@Preview
@Composable
fun SearchBarPreview() {
  TopAppSearchBar(
    text = "Test!",
    onTextChange = {},
    onClose = {},
    onSearch = {}
  )
}