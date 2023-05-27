package com.flowyh.letmecook.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.flowyh.letmecook.R
import com.flowyh.letmecook.ui.components.DefaultScreenWithoutSearchbar
import com.flowyh.letmecook.ui.theme.*
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun ShoppingListScreen(
  viewModel: MainBundledViewModel,
  navController: NavController,
  navigator: DestinationsNavigator,
  resultNavigator: ResultBackNavigator<Unit>
) {
  // UI state
  val scope = rememberCoroutineScope()
  val snackBarHostState = remember { SnackbarHostState() }
  val listState = rememberLazyListState()

  val shoppingLists = viewModel.shoppingLists.collectAsStateWithLifecycle()

  DefaultScreenWithoutSearchbar(
    navController = navController,
    onNavigationClick = {
      resultNavigator.navigateBack()
    },
    snackBarHostState = snackBarHostState
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(
            horizontal = MaterialTheme.spacing.medium,
            vertical = MaterialTheme.spacing.small
          ),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
      ) {
        items(shoppingLists.value.size) { item ->
          RecipeScreenIngredients(
            modifier = Modifier,
            cardTitle = shoppingLists.value[item].recipeTitle,
            cardTitleTypography = MaterialTheme.typography.headlineSmall,
            cardTopLeadingContent = { modifier ->
              // Jump to recipe icon
              IconButton(
                onClick = {
                  viewModel.shoppingListViewModel.jumpToRecipeFromId(shoppingLists.value[item].recipeId, navigator)
                },
                modifier = modifier
                  .padding(end = MaterialTheme.spacing.small)
                  .size(24.dp)
              ) {
                Icon(
                  imageVector = launchIcon,
                  contentDescription = stringResource(R.string.launch_icon_content_description),
                  tint = MaterialTheme.colorScheme.primary
                )
              }
            },
            cardTopTrailingContent = { modifier ->
              // Delete shopping list icon
              val removeSnackBarText = stringResource(R.string.shopping_list_remove_content_description)
              IconButton(
                onClick = {
                  viewModel.shoppingListViewModel.deleteShoppingList(shoppingLists.value[item])
                  scope.launch {
                    snackBarHostState.showSnackbar(
                      message = removeSnackBarText,
                    )
                  }
                },
                modifier = modifier
                  .padding(end = MaterialTheme.spacing.small)
                  .size(24.dp)
              ) {
                Icon(
                  imageVector = deleteIcon,
                  contentDescription = stringResource(R.string.shopping_list_delete_icon_content_description),
                  tint = MaterialTheme.colorScheme.primary
                )
              }
            },
            ingredients = shoppingLists.value[item].ingredients,
            abi = shoppingLists.value[item].alreadyBoughtIngredients,
          ) { _, ind -> // On checkbox click
            if(shoppingLists.value[item].alreadyBoughtIngredients.contains((ind))){
              shoppingLists.value[item].alreadyBoughtIngredients =
                shoppingLists.value[item].alreadyBoughtIngredients.filter{it != ind}

            } else{
              shoppingLists.value[item].alreadyBoughtIngredients =
                shoppingLists.value[item].alreadyBoughtIngredients + ind
            }

            viewModel.shoppingListViewModel.updateShoppingListABI(shoppingLists.value[item], item, shoppingLists.value[item].alreadyBoughtIngredients)
          }
        }
      }
    }
  }
}
