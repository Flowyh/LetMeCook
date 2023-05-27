package com.flowyh.letmecook.viewmodels

import android.util.Log
import androidx.compose.runtime.rememberCompositionContext
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowyh.letmecook.controllers.repositories.FirestoreRepositoryImpl
import com.flowyh.letmecook.controllers.repositories.RoomRepositoryImpl
import com.flowyh.letmecook.models.*
import com.flowyh.letmecook.ui.screens.destinations.RecipeScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt

class ShoppingListViewModel(
    private val savedStateHandle: SavedStateHandle,
    var roomRepository: RoomRepositoryImpl
): ViewModel() {

    val db = FirestoreRepositoryImpl()
    var shoppingLists: List<ShoppingList> = roomRepository.getShoppingLists()
    val shoppingListsState = savedStateHandle.getStateFlow("shoppingLists", shoppingLists)


    fun deleteShoppingList(shoppingList: ShoppingList){
        roomRepository.delete(shoppingList)
        shoppingLists = shoppingLists.filter{ it != shoppingList }
        savedStateHandle["shoppingLists"] = shoppingLists
    }

    fun addShoppingList(shoppingList: ShoppingList){
        roomRepository.insertAllShoppingLists(listOf(shoppingList))
        shoppingLists = shoppingLists + shoppingList
        savedStateHandle["shoppingLists"] = shoppingLists
    }

    fun updateShoppingListABI(shoppingList: ShoppingList, index: Int, abi: List<Int>){
        roomRepository.updateAlreadyBoughtIngredients(shoppingList.id, abi)
        shoppingLists[index].alreadyBoughtIngredients = abi
        savedStateHandle["shoppingLists"] = shoppingLists
    }

    fun jumpToRecipeFromId(id: String, navigator: DestinationsNavigator){
        viewModelScope.launch {
            val recipesList = db.getAllRecipes()
            val recipe = recipesList.filter { it.id == id }
            navigator.navigate(
                RecipeScreenDestination(
                    recipe = recipe[0]
                )
            )
        }
    }

}

