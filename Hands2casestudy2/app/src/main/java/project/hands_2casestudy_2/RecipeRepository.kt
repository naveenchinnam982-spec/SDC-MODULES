package project.hands_2casestudy_2

import android.content.Context
import org.json.JSONArray

object RecipeRepository {
    fun loadRecipes(context: Context): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        try {
            val jsonString = context.assets.open("recipes.json").bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val ingredients = mutableListOf<String>()
                val ingredientsArray = jsonObject.getJSONArray("ingredients")
                for (j in 0 until ingredientsArray.length()) {
                    ingredients.add(ingredientsArray.getString(j))
                }
                
                val instructions = mutableListOf<String>()
                val instructionsArray = jsonObject.getJSONArray("instructions")
                for (j in 0 until instructionsArray.length()) {
                    instructions.add(instructionsArray.getString(j))
                }

                recipes.add(
                    Recipe(
                        id = jsonObject.getInt("id"),
                        title = jsonObject.getString("title"),
                        imageUrl = jsonObject.getString("imageUrl"),
                        ingredients = ingredients,
                        instructions = instructions
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return recipes
    }
}
