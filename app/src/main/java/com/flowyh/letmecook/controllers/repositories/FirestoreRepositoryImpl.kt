package com.flowyh.letmecook.controllers.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.flowyh.letmecook.controllers.interfaces.FirestoreRepository
import com.flowyh.letmecook.models.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor() : FirestoreRepository {

    private val db = Firebase.firestore

    private val idLookupTable = arrayListOf(
        "BI", "CiB", "CiC", "JaF", "KWA",
        "KaP", "LE", "MUF", "NA", "OgF",
        "PI", "PaK", "PiC", "PlB", "PlR",
        "SaT", "SaZ", "SuM", "TAB", "ZaG", "ZuC"
    )


    override suspend fun getAllRecipes(): List<Recipe> {
        val recipeBodies       = ArrayList<DocumentSnapshot>()
        val recipeDescriptions = ArrayList<DocumentSnapshot>()
        val recipeIngredients  = ArrayList<DocumentSnapshot>()

        val recipeList = ArrayList<Recipe>()


        // ----- Get recipe bodies -----
        db.collection("recipe_body")
            .get()
            .addOnSuccessListener { recipes ->
                for (document in recipes) {
                    recipeBodies.add(document)
                }
                Log.d(TAG, "Document obtained successfully!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting collection", e) }
            .await()


        // ----- Get ingredients -----
        db.collection("ingredients_list")
            .get()
            .addOnSuccessListener { ingredients ->
                for (document in ingredients) {
                    recipeIngredients.add(document)
                }
                Log.d(TAG, "Document obtained successfully!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting collection", e) }
            .await()


        // ----- Get descriptions -----
        db.collection("description")
            .get()
            .addOnSuccessListener { descriptions ->
                for (document in descriptions) {
                    recipeDescriptions.add(document)
                }
                Log.d(TAG, "Document obtained successfully!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting collection", e) }
            .await()

        // ----- Create Recipe list -----
        for (recipe in recipeDescriptions) {
            val recId   = recipe.id
            val recBody = recipeBodies.find {
                it.id == recId
            }!!

            // ----- Create RecipeIngredients list for RecipeDetails  -----
            val ingredientsList = getRecipeIngredients(recId, recipeIngredients)

            // ----- Create RecipeFilter list for RecipeDetails -----
            val filtersList = getRecipeFilters(recBody)

            // ----- Create RecipeDetails for Recipe -----
            val bodyDescription = recBody.getString("description")!!
            Log.d(TAG, "BEFORE RECIPEBODY: ${recBody}")
            val bodyImage       = recipe.getString("url_main")!!
            Log.d(TAG, "AFTER RECIPEBODY: ${recBody}  ||||   ${bodyImage}")

            val bodySteps       = recBody.get("steps") as ArrayList<String>


            // ----- Create Recipe -----
            val recTitle      = recipe.getString("title")!!
            val recTime       = "${recipe.get("time")} min"
            val recDifficulty = recipe.getField<Int>("difficulty")!!
            val recServings   = recipe.getField<Int>("portions")!!
            val recRating     = recBody.getField<Float>("rating")!!
            val recImage      = recBody.getString("detailed_url")!!
            val recDetails    = createRecipeDetails(
                bigImage = bodyImage,
                description = bodyDescription,
                ingredients = ingredientsList,
                steps = bodySteps,
                filters = filtersList
            )!!


            val newRecipe = createRecipe(
                title = recTitle,
                time = recTime,
                difficulty = recDifficulty,
                servings = recServings,
                rating = recRating,
                smallImage = recImage,
                details = recDetails,
                id = recId
            )


            if (newRecipe != null) {
                recipeList.add(newRecipe)
            }
        }


        return recipeList
    }


    override suspend fun getRecipeByIndex(index: Int): Recipe {
        val recipeId = idLookupTable[index]
        val recipeIngredients = ArrayList<DocumentSnapshot>()


        // ----- Get recipe body -----
        val recipeBody = db.collection("recipe_body")
            .document(recipeId)
            .get()
            .addOnSuccessListener { Log.d(TAG, "Document obtained successfully!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting document", e) }
            .await()


        // ----- Get recipe description -----
        val recipeDescription = db.collection("description")
            .document(recipeId)
            .get()
            .addOnSuccessListener { Log.d(TAG, "Document obtained successfully!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting document", e) }
            .await()


        // ----- Get ingredients -----
        db.collection("ingredients_list")
            .whereEqualTo("recipe_id", recipeId)
            .get()
            .addOnSuccessListener { ingredients ->
                for (document in ingredients) {
                    recipeIngredients.add(document)
                }
                Log.d(TAG, "Document obtained successfully!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting document", e) }
            .await()


        // ----- Create RecipeIngredients list for RecipeDetails  -----
        val ingredientsList = getRecipeIngredients(recipeId, recipeIngredients)

        // ----- Create RecipeFilter list for RecipeDetails -----
        val filtersList = getRecipeFilters(recipeBody)

        // ----- Create RecipeDetails for Recipe -----
        val bodyDescription = recipeBody.getString("description")!!
        val bodyImage       = recipeDescription.getString("url_main")!!
        val bodySteps       = recipeBody.get("steps") as ArrayList<String>


        // ----- Create Recipe -----
        val recTitle      = recipeDescription.getString("title")!!
        val recTime       = "${recipeDescription.get("time")} min"
        val recDifficulty = recipeDescription.getField<Int>("difficulty")!!
        val recServings   = recipeDescription.getField<Int>("portions")!!
        val recRating     = recipeBody.getField<Float>("rating")!!
        val recImage      = recipeBody.getString("detailed_url")!!
        val recDetails    = createRecipeDetails(
            bigImage = bodyImage,
            description = bodyDescription,
            ingredients = ingredientsList,
            steps = bodySteps,
            filters = filtersList
        )!!


        return createRecipe(
            title = recTitle,
            time = recTime,
            difficulty = recDifficulty,
            servings = recServings,
            rating = recRating,
            smallImage = recImage,
            details = recDetails,
            id = recipeId
        )!!
    }




    override suspend fun getRecipeById(recipeId: String): Recipe {
        val recipeIngredients = ArrayList<DocumentSnapshot>()


        // ----- Get recipe body -----
        val recipeBody = db.collection("recipe_body")
            .document(recipeId)
            .get()
            .addOnSuccessListener { Log.d(TAG, "Document obtained successfully!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting document", e) }
            .await()

        // ----- Get recipe description -----
        val recipeDescription = db.collection("description")
            .document(recipeId)
            .get()
            .addOnSuccessListener { Log.d(TAG, "Document obtained successfully!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting document", e) }
            .await()

        // ----- Get ingredients -----
        db.collection("ingredients_list")
            .whereEqualTo("recipe_id", recipeId)
            .get()
            .addOnSuccessListener { ingredients ->
                for (document in ingredients) {
                    recipeIngredients.add(document)
                }
                Log.d(TAG, "Document obtained successfully!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting document", e) }
            .await()


        // ----- Create RecipeIngredients list for RecipeDetails  -----
        val ingredientsList = getRecipeIngredients(recipeId, recipeIngredients)

        // ----- Create RecipeFilter list for RecipeDetails -----
        val filtersList = getRecipeFilters(recipeBody)

        // ----- Create RecipeDetails for Recipe -----
        val bodyDescription = recipeBody.getString("description")!!
        val bodyImage       = recipeDescription.getString("url_main")!!
        val bodySteps       = recipeBody.get("steps") as ArrayList<String>


        // ----- Create Recipe -----
        val recTitle      = recipeDescription.getString("title")!!
        val recTime       = "${recipeDescription.get("time")} min"
        val recDifficulty = recipeDescription.getField<Int>("difficulty")!!
        val recServings   = recipeDescription.getField<Int>("portions")!!
        val recRating     = recipeBody.getField<Float>("rating")!!
        val recImage      = recipeBody.getString("detailed_url")!!
        val recDetails    = createRecipeDetails(
            bigImage = bodyImage,
            description = bodyDescription,
            ingredients = ingredientsList,
            steps = bodySteps,
            filters = filtersList
        )!!


        return createRecipe(
            title = recTitle,
            time = recTime,
            difficulty = recDifficulty,
            servings = recServings,
            rating = recRating,
            smallImage = recImage,
            details = recDetails,
            id = recipeId
        )!!
    }


    override suspend fun getRecipesForFilter(filters: List<String>): List<Recipe> {
        val recipeIngredients =  ArrayList<DocumentSnapshot>()

        val recipeList    = ArrayList<Recipe>()
        val recipesIdList = ArrayList<String>()


        // ----- Get recipe bodies matching filters -----
        val recipeBodies = db.collection("recipe_body")
            .whereArrayContainsAny("filters", filters)
            .get()
            .addOnSuccessListener { recipes ->
                for (document in recipes) {
                    recipesIdList.add(document.id)
                }
                Log.d(TAG, "Document obtained successfully!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting collection", e) }
            .await()



        // ----- Get descriptions matching filters -----
        val recipeDescriptions = db.collection("description")
            .whereIn(FieldPath.documentId(), recipesIdList)
            .get()
            .addOnSuccessListener { Log.d(TAG, "Document obtained successfully!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting collection", e) }
            .await()


        // ----- Get ingredients matching filters -----
        db.collection("ingredients_list")
            .whereIn("recipe_id", recipesIdList)
            .get()
            .addOnSuccessListener { ingredients ->
                for (document in ingredients) {
                    recipeIngredients.add(document)
                }
                Log.d(TAG, "Document obtained successfully!")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting collection", e) }
            .await()



        // ----- Create Recipe list -----
        for (recipe in recipeDescriptions) {
            val recId   = recipe.id
            val recBody = recipeBodies.find {
                it.id == recId
            }!!


            // ----- Create RecipeIngredients list for RecipeDetails  -----
            val ingredientsList = getRecipeIngredients(recId, recipeIngredients)

            // ----- Create RecipeFilter list for RecipeDetails -----
            val filtersList = getRecipeFilters(recBody)

            // ----- Create RecipeDetails for Recipe -----
            val bodyDescription = recBody.getString("description")!!
            val bodyImage       = recipe.getString("url_main")!!
            val bodySteps       = recBody.get("steps") as ArrayList<String>


            // ----- Create Recipe -----
            val recTitle      = recipe.getString("title")!!
            val recTime       = "${recipe.get("time")} min"
            val recDifficulty = recipe.getField<Int>("difficulty")!!
            val recServings   = recipe.getField<Int>("portions")!!
            val recRating     = recBody.getField<Float>("rating")!!
            val recImage      = recBody.getString("detailed_url")!!
            val recDetails    = createRecipeDetails(
                bigImage = bodyImage,
                description = bodyDescription,
                ingredients = ingredientsList,
                steps = bodySteps,
                filters = filtersList
            )!!


            val newRecipe = createRecipe(
                title = recTitle,
                time = recTime,
                difficulty = recDifficulty,
                servings = recServings,
                rating = recRating,
                smallImage = recImage,
                details = recDetails,
                id = recId
            )


            if (newRecipe != null) {
                recipeList.add(newRecipe)
            }
        }


        return recipeList
    }


    override suspend fun getCourseFilters(): List<RecipeFilter> {
        val courseFiltersList = ArrayList<RecipeFilter>()


        // ----- Get course filters -----
        val courseFilters = db.collection("filters")
            .document("Course")
            .get()
            .addOnSuccessListener { Log.d(TAG, "Document obtained successfully!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error getting collection", e) }
            .await()


        val filterNames = courseFilters.get("types") as ArrayList<String>

        for (name in filterNames) {
            createRecipeFilter(
                FilterType.COURSE, name
            )?.let {
                courseFiltersList.add(
                    it
                )
            }
        }


        return courseFiltersList
    }



    private fun getRecipeIngredients(recipeId: String, recipeIngredients: List<DocumentSnapshot>): List<RecipeIngredient> {
        val ingredientsList = ArrayList<RecipeIngredient>()
        val ingredients = recipeIngredients.filter {
            it.get("recipe_id") == recipeId
        }


        for (ingredient in ingredients) {
            val ingName     = ingredient.getString("name")!!
            val ingQuantity = ingredient.getDouble("quantity")!!
            val ingUnit     = ingredient.getString("unit")!!
            val ingType     = ingredient.getField<IngredientType>("type")!!

            createRecipeIngredient(
                ingName, ingQuantity, ingUnit, ingType
            )?.let {
                ingredientsList.add(
                    it
                )
            }
        }


        return ingredientsList
    }

    private fun getRecipeFilters(recBody: DocumentSnapshot): List<RecipeFilter> {
        val filtersList = ArrayList<RecipeFilter>()
        val filtersNames = recBody.get("filters") as ArrayList<String>


        for (i in 0..2) {
            val filterType = when (i) {
                0 -> FilterType.CUISINE
                1 -> FilterType.DIET
                2 -> FilterType.COURSE
                else -> FilterType.ALL
            }

            createRecipeFilter(
                filterType, filtersNames[i]
            )?.let {
                filtersList.add(
                    it
                )
            }
        }


        return filtersList
    }
}
