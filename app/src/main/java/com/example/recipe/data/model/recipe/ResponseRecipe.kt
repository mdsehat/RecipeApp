package com.example.recipe.data.model.recipe


import com.google.gson.annotations.SerializedName

data class ResponseRecipe(
    @SerializedName("number")
    val number: Int?, // 10
    @SerializedName("offset")
    val offset: Int?, // 0
    @SerializedName("results")
    val results: MutableList<Result>?,
    @SerializedName("totalResults")
    val totalResults: Int? // 5221
) {
    data class Result(
        @SerializedName("aggregateLikes")
        val aggregateLikes: Int?, // 32767
        @SerializedName("analyzedInstructions")
        val analyzedInstructions: List<AnalyzedInstruction?>?,
        @SerializedName("cheap")
        val cheap: Boolean?, // false
        @SerializedName("cookingMinutes")
        val cookingMinutes: Int?, // 20
        @SerializedName("creditsText")
        val creditsText: String?, // Jen West
        @SerializedName("cuisines")
        val cuisines: List<String?>?,
        @SerializedName("dairyFree")
        val dairyFree: Boolean?, // false
        @SerializedName("diets")
        val diets: List<String?>?,
        @SerializedName("dishTypes")
        val dishTypes: List<String?>?,
        @SerializedName("gaps")
        val gaps: String?, // no
        @SerializedName("glutenFree")
        val glutenFree: Boolean?, // false
        @SerializedName("healthScore")
        val healthScore: Int?, // 20
        @SerializedName("id")
        val id: Int?, // 715449
        @SerializedName("image")
        val image: String?, // https://spoonacular.com/recipeImages/715449-312x231.jpg
        @SerializedName("imageType")
        val imageType: String?, // jpg
        @SerializedName("license")
        val license: String?, // CC BY-SA 3.0
        @SerializedName("lowFodmap")
        val lowFodmap: Boolean?, // false
        @SerializedName("occasions")
        val occasions: List<String?>?,
        @SerializedName("preparationMinutes")
        val preparationMinutes: Int?, // 20
        @SerializedName("pricePerServing")
        val pricePerServing: Double?, // 633.3
        @SerializedName("readyInMinutes")
        val readyInMinutes: Int?, // 40
        @SerializedName("servings")
        val servings: Int?, // 48
        @SerializedName("sourceName")
        val sourceName: String?, // Pink When
        @SerializedName("sourceUrl")
        val sourceUrl: String?, // https://www.pinkwhen.com/oreo-cookie-balls-thanksgiving-turkey/
        @SerializedName("spoonacularSourceUrl")
        val spoonacularSourceUrl: String?, // https://spoonacular.com/how-to-make-oreo-turkeys-for-thanksgiving-715449
        @SerializedName("summary")
        val summary: String?, // How to Make OREO Turkeys for Thanksgiving requires about <b>40 minutes</b> from start to finish. This recipe serves 48. One serving contains <b>835 calories</b>, <b>47g of protein</b>, and <b>45g of fat</b>. For <b>$6.33 per serving</b>, this recipe <b>covers 27%</b> of your daily requirements of vitamins and minerals. 32767 people were impressed by this recipe. Head to the store and pick up oreo cookies 3 cups, icing, semi baking chocolate, and a few other things to make it today. It can be enjoyed any time, but it is especially good for <b>Thanksgiving</b>. It is brought to you by Pink When. Taking all factors into account, this recipe <b>earns a spoonacular score of 18%</b>, which is rather bad. Similar recipes are <a href="https://spoonacular.com/recipes/how-to-make-oreo-turkeys-for-thanksgiving-1364303">How to Make OREO Turkeys for Thanksgiving</a>, <a href="https://spoonacular.com/recipes/oreo-turkeys-thanksgiving-snack-138063">Oreo Turkeys (Thanksgiving Snack)</a>, and <a href="https://spoonacular.com/recipes/cakespy-thanksgiving-cookie-turkeys-50158">Cakespy: Thanksgiving Cookie Turkeys</a>.
        @SerializedName("sustainable")
        val sustainable: Boolean?, // false
        @SerializedName("title")
        val title: String?, // How to Make OREO Turkeys for Thanksgiving
        @SerializedName("vegan")
        val vegan: Boolean?, // false
        @SerializedName("vegetarian")
        val vegetarian: Boolean?, // false
        @SerializedName("veryHealthy")
        val veryHealthy: Boolean?, // false
        @SerializedName("veryPopular")
        val veryPopular: Boolean?, // true
        @SerializedName("weightWatcherSmartPoints")
        val weightWatcherSmartPoints: Int? // 32
    ) {
        data class AnalyzedInstruction(
            @SerializedName("name")
            val name: String?,
            @SerializedName("steps")
            val steps: List<Step?>?
        ) {
            data class Step(
                @SerializedName("equipment")
                val equipment: List<Equipment?>?,
                @SerializedName("ingredients")
                val ingredients: List<Ingredient?>?,
                @SerializedName("length")
                val length: Length?,
                @SerializedName("number")
                val number: Int?, // 1
                @SerializedName("step")
                val step: String? // Take a package of OREO cookies and crush them up finely.
            ) {
                data class Equipment(
                    @SerializedName("id")
                    val id: Int?, // 404727
                    @SerializedName("image")
                    val image: String?, // baking-sheet.jpg
                    @SerializedName("localizedName")
                    val localizedName: String?, // baking sheet
                    @SerializedName("name")
                    val name: String? // baking sheet
                )

                data class Ingredient(
                    @SerializedName("id")
                    val id: Int?, // 10018166
                    @SerializedName("image")
                    val image: String?, // oreos.png
                    @SerializedName("localizedName")
                    val localizedName: String?, // oreo cookies
                    @SerializedName("name")
                    val name: String? // oreo cookies
                )

                data class Length(
                    @SerializedName("number")
                    val number: Int?, // 10
                    @SerializedName("unit")
                    val unit: String? // minutes
                )
            }
        }
    }
}