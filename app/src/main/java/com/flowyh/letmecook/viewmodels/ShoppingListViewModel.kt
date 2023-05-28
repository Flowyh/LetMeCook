package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowyh.letmecook.controllers.interfaces.FirestoreRepository
import com.flowyh.letmecook.controllers.repositories.RoomRepositoryImpl
import com.flowyh.letmecook.models.*
import com.flowyh.letmecook.ui.screens.destinations.RecipeScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

class ShoppingListViewModel(
    private val firestoreRepository: FirestoreRepository,
    private val roomRepository: RoomRepositoryImpl,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var shoppingLists: List<ShoppingList> = roomRepository.getShoppingLists()
    val shoppingListsState = savedStateHandle.getStateFlow("shoppingLists", shoppingLists)

    private fun reloadData() {
        viewModelScope.launch {
            shoppingLists = roomRepository.getShoppingLists()
        }
    }

    init {
        reloadData()
    }


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
            val recipesList = firestoreRepository.getAllRecipes()
            val recipe = recipesList.filter { it.id == id }
            navigator.navigate(
                RecipeScreenDestination(
                    recipe = recipe[0]
                )
            )
        }
    }

}

