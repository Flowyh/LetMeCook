package com.flowyh.letmecook.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.flowyh.letmecook.models.*

class HomeScreenViewModel(
  savedStateHandle: SavedStateHandle = SavedStateHandle(),
  recipeListViewModel: RecipeListViewModel = RecipeListViewModel(savedStateHandle),
  val recipeFiltersViewModel: RecipeFiltersViewModel =
    RecipeFiltersViewModel(savedStateHandle, recipeListViewModel::filterRecipeList)
) : ViewModel() {
  val recipes = recipeListViewModel.recipes
  val filters = recipeFiltersViewModel.filters

  val currentSelectedRecipe = createRecipe(
    title = "Recipe 1",
    time = "12h 30min",
    difficulty = 3,
    servings = 12,
    details = createRecipeDetails(
      description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce quis diam vitae arcu consequat accumsan. Cras vehicula gravida bibendum. Curabitur vulputate scelerisque interdum. Sed blandit rhoncus augue, sed volutpat purus aliquam placerat. Sed quis rutrum est. Morbi euismod diam ac nisl molestie, a tincidunt felis scelerisque. Aenean eget dignissim turpis, eu pretium sem. Nulla blandit, neque ac congue facilisis, erat risus pretium nulla, sed mollis magna purus sed dui. Vestibulum tempus est ac enim aliquet condimentum.\n" +
              "\n" +
              "Etiam malesuada suscipit leo, at tincidunt leo vulputate sit amet. Sed vulputate tellus ut justo vulputate molestie. Ut fermentum erat quis nibh cursus, ut vestibulum dui molestie. Ut interdum felis sit amet dolor mollis dictum. Integer a est augue. Praesent iaculis, risus sed euismod aliquet, neque neque porta dui, ac hendrerit est erat quis erat. Pellentesque nec elit ut lacus mollis dapibus. Vestibulum scelerisque, odio sit amet sodales lobortis, odio ante commodo ligula, et luctus purus mauris a sapien. Proin metus nulla, faucibus vitae felis non, pharetra rhoncus ligula. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer tristique, tellus nec vulputate placerat, augue eros auctor lacus, sed feugiat leo massa vel risus. Maecenas ac bibendum erat.",
      ingredients = listOf(
        createRecipeIngredient(
          name = "Ingredient 1",
          quantity = 0.5,
          unit = "kg",
          type = IngredientType.OTHER
        )!!,
        createRecipeIngredient(
          name = "Ingredient 2",
          quantity = 4.0,
          unit = "cups",
          type = IngredientType.OTHER
        )!!,
        createRecipeIngredient(
          name = "Ingredient 3",
          quantity = 3.0,
          unit = "tsp",
          type = IngredientType.OTHER
        )!!,
      ),
      steps = listOf(
        "Cook the living shit out of it",
        "Throw me some numbers",
        "Super loooooooooooooooooooong looooooooooooooooooooooooooooooooooooong liiiiiiiiiiiiiiiiiiiiine"
      ),
      rating = 4.5f,
      filters = listOf(
        createRecipeFilter(
          type = FilterType.CUISINE,
          name = "Italian"
        )!!,
        createRecipeFilter(
          type = FilterType.COURSE,
          name = "Dessert"
        )!!,
      )
    )!!
  )!!

  val searchBarViewModel = SearchBarViewModel(
    savedStateHandle,
    recipeListViewModel::updateRecipeList
  )
}
