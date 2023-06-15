package com.example.recipe.data.model.lucky


import com.example.recipe.data.model.detail.DetailResponse
import com.google.gson.annotations.SerializedName

data class ResponseLucky(
    @SerializedName("recipes")
    val recipes: List<Recipe>?
) {
    data class Recipe(
        @SerializedName("aggregateLikes")
        val aggregateLikes: Int?, // 13
        @SerializedName("analyzedInstructions")
        val analyzedInstructions: List<DetailResponse.AnalyzedInstruction>?,
        @SerializedName("cheap")
        val cheap: Boolean?, // false
        @SerializedName("cookingMinutes")
        val cookingMinutes: Int?, // -1
        @SerializedName("creditsText")
        val creditsText: String?, // Foodista.com – The Cooking Encyclopedia Everyone Can Edit
        @SerializedName("cuisines")
        val cuisines: List<Any?>?,
        @SerializedName("dairyFree")
        val dairyFree: Boolean?, // true
        @SerializedName("diets")
        val diets: List<String>?,
        @SerializedName("dishTypes")
        val dishTypes: List<String?>?,
        @SerializedName("extendedIngredients")
        val extendedIngredients: List<DetailResponse.ExtendedIngredient>?,
        @SerializedName("gaps")
        val gaps: String?, // no
        @SerializedName("glutenFree")
        val glutenFree: Boolean?, // true
        @SerializedName("healthScore")
        val healthScore: Int?, // 3
        @SerializedName("id")
        val id: Int?, // 662276
        @SerializedName("image")
        val image: String?, // https://spoonacular.com/recipeImages/662276-556x370.jpg
        @SerializedName("imageType")
        val imageType: String?, // jpg
        @SerializedName("instructions")
        val instructions: String?, // <ol><li>Preheat oven to 350 F.</li><li>Cover the inside of one ramekin or oven safe dish with olive oil.</li><li>Line the bottom of the dish with sundried tomatoes and break two eggs on top. Sprinkle with fresh herbs.</li><li>Bake uncovered for 15-20 minutes until egg whites are firm.</li><li>Serve immediately.</li></ol>
        @SerializedName("license")
        val license: String?, // CC BY 3.0
        @SerializedName("lowFodmap")
        val lowFodmap: Boolean?, // true
        @SerializedName("occasions")
        val occasions: List<Any?>?,
        @SerializedName("originalId")
        val originalId: Any?, // null
        @SerializedName("preparationMinutes")
        val preparationMinutes: Int?, // -1
        @SerializedName("pricePerServing")
        val pricePerServing: Double?, // 90.05
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int?, // 25
        @SerializedName("servings")
        val servings: Int?, // 1
        @SerializedName("sourceName")
        val sourceName: String?, // Foodista
        @SerializedName("sourceUrl")
        val sourceUrl: String?, // http://www.foodista.com/recipe/V4LG5KBB/sundried-tomato-and-herb-baked-eggs
        @SerializedName("spoonacularSourceUrl")
        val spoonacularSourceUrl: String?, // https://spoonacular.com/sun-dried-tomato-and-herb-baked-eggs-662276
        @SerializedName("summary")
        val summary: String?, // If you want to add more <b>gluten free, dairy free, paleolithic, and lacto ovo vegetarian</b> recipes to your recipe box, Sun Dried Tomato and Herb Baked Eggs might be a recipe you should try. One serving contains <b>179 calories</b>, <b>12g of protein</b>, and <b>13g of fat</b>. This recipe serves 1. For <b>90 cents per serving</b>, this recipe <b>covers 10%</b> of your daily requirements of vitamins and minerals. This recipe from Foodista requires eggs, basil, sundried tomatoes, and olive oil. 13 people found this recipe to be flavorful and satisfying. It works best as a hor d'oeuvre, and is done in around <b>25 minutes</b>. Taking all factors into account, this recipe <b>earns a spoonacular score of 37%</b>, which is not so super. Similar recipes include <a href="https://spoonacular.com/recipes/sun-dried-tomato-and-herb-baked-eggs-1365689">Sun Dried Tomato and Herb Baked Eggs</a>, <a href="https://spoonacular.com/recipes/baked-eggs-with-spinach-mushrooms-and-sun-dried-tomato-530601">Baked Eggs with Spinach, Mushrooms and Sun Dried Tomato</a>, and <a href="https://spoonacular.com/recipes/herb-sun-dried-tomato-muffins-462519">Herb & Sun-Dried Tomato Muffins</a>.
        @SerializedName("sustainable")
        val sustainable: Boolean?, // false
        @SerializedName("title")
        val title: String?, // Sun Dried Tomato and Herb Baked Eggs
        @SerializedName("vegan")
        val vegan: Boolean?, // false
        @SerializedName("vegetarian")
        val vegetarian: Boolean?, // true
        @SerializedName("veryHealthy")
        val veryHealthy: Boolean?, // false
        @SerializedName("veryPopular")
        val veryPopular: Boolean?, // false
        @SerializedName("weightWatcherSmartPoints")
        val weightWatcherSmartPoints: Int? // 4
    )
}