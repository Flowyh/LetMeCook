package com.flowyh.letmecook.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.flowyh.letmecook.controllers.interfaces.RoomRepositoryDao
import com.flowyh.letmecook.controllers.repositories.FirestoreRepositoryImpl
import com.flowyh.letmecook.models.IngredientType
import com.flowyh.letmecook.models.RecipeIngredient
import com.flowyh.letmecook.models.ShoppingList
import com.flowyh.letmecook.ui.components.DefaultScreenWithoutSearchbar
import com.flowyh.letmecook.ui.components.FavoriteRecipeListItem
import com.flowyh.letmecook.ui.screens.destinations.RecipeScreenDestination
import com.flowyh.letmecook.ui.theme.*
import com.flowyh.letmecook.viewmodels.MainBundledViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.gson.Gson
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.launch
import java.time.LocalDateTime
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

    viewModel.roomRepository.getShoppingLists().forEach({
            Log.i("SL", Gson().toJson(it))
        }
    )

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
/*                                    suspend {
                                        var recipes = viewModel.shoppingListViewModel.db.getAllRecipes()
                                        recipes = recipes.filter { it.id == shoppingLists.value[item].recipeId}
                                        navigator.navigate(
                                            RecipeScreenDestination(
                                                recipe = recipes[0]
                                            )
                                        )
                                    }*/
                                        viewModel.shoppingListViewModel.jumpToRecipeFromId(shoppingLists.value[item].recipeId, navigator)
                                    // TODO: jump to recipe based on shopping list id (getRecipeById in viewModel?)
                                          Log.i("SL", shoppingLists.value[item].recipeId)
                                },
                                modifier = modifier
                                    .padding(end = MaterialTheme.spacing.small)
                                    .size(24.dp)
                            ) {
                                Icon(
                                    imageVector = launchIcon,
                                    contentDescription = "go to recipe",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        cardTopTrailingContent = { modifier ->
                            // Delete shopping list icon
                            IconButton(
                                onClick = {
                                    // TODO: remove shopping list in room
                                    /*viewModel.roomRepository.delete(shoppingLists.value[item])*/
                                    viewModel.shoppingListViewModel.deleteShoppingList(shoppingLists.value[item])
                                    /*shoppingLists.value = shoppingLists.value.filter{ it != shoppingLists.value[item] }*/
                                },
                                modifier = modifier
                                    .padding(end = MaterialTheme.spacing.small)
                                    .size(24.dp)
                            ) {
                                Icon(
                                    imageVector = deleteIcon,
                                    contentDescription = "delete shopping list",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        ingredients = shoppingLists.value[item].ingredients,
                        abi = shoppingLists.value[item].alreadyBoughtIngredients,
                    ) { ingredientState, ind -> // On checkbox click
                        if(shoppingLists.value[item].alreadyBoughtIngredients.contains((ind))){
                            shoppingLists.value[item].alreadyBoughtIngredients =
                                shoppingLists.value[item].alreadyBoughtIngredients.filter{it != ind}

                        } else{
                            shoppingLists.value[item].alreadyBoughtIngredients =
                                shoppingLists.value[item].alreadyBoughtIngredients + ind
                        }

                        viewModel.shoppingListViewModel.updateShoppingListABI(shoppingLists.value[item], item, shoppingLists.value[item].alreadyBoughtIngredients)
                        // TODO: change list ingredient state based on boolean value (use viewmodel)
                    }
                }
            }
        }
    }
}
