package com.example.recipe.data.model.detail


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class DetailResponse(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int?, // 309
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstruction>?,
    @SerializedName("cheap")
    val cheap: Boolean?, // false
    @SerializedName("cookingMinutes")
    val cookingMinutes: Int?, // -1
    @SerializedName("creditsText")
    val creditsText: String?, // blogspot.com
    @SerializedName("cuisines")
    val cuisines: List<Any?>?,
    @SerializedName("dairyFree")
    val dairyFree: Boolean?, // true
    @SerializedName("diets")
    val diets: List<String>?,
    @SerializedName("dishTypes")
    val dishTypes: List<String?>?,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>?,
    @SerializedName("gaps")
    val gaps: String?, // no
    @SerializedName("glutenFree")
    val glutenFree: Boolean?, // true
    @SerializedName("healthScore")
    val healthScore: Int?, // 100
    @SerializedName("id")
    val id: Int?, // 782585
    @SerializedName("image")
    val image: String?, // https://spoonacular.com/recipeImages/782585-556x370.jpg
    @SerializedName("imageType")
    val imageType: String?, // jpg
    @SerializedName("instructions")
    val instructions: String?, // Rinse the cannellini beans and soak for 8 hours or overnight in several inches of water. Drain and rinse, then transfer to a medium saucepan and cover with fresh water. Add the curry leaves or bay leaf and bring to a boil. Reduce heat to medium-low, cover, and simmer for 1 hour or until the beans are tender but not falling apart. Drain and transfer to a large salad bowl.Meanwhile, snap the woody ends off of the asparagus spears and steam the spears for 6 minutes or until just tender but still retaining their crunch. Transfer to the salad bowl.Now cook the mushrooms. Heat the oil in a large skillet over high heat. As soon as the oil is hot, drop in the mushrooms and cook, stirring constantly, for 5 minutes or until the mushrooms begin to brown and lose some of their liquid. Transfer to the bowl with the asparagus.To make the dressing, combine the tarragon, lemon zest, garlic, lemon juice, olive oil and mustard in a small food processor or blender. Process until smooth.Pour the dressing over the salad, season with salt and pepper, and toss. Serve at room temperature or chilled.
    @SerializedName("lowFodmap")
    val lowFodmap: Boolean?, // false
    @SerializedName("occasions")
    val occasions: List<Any?>?,
    @SerializedName("originalId")
    val originalId: Any?, // null
    @SerializedName("preparationMinutes")
    val preparationMinutes: Int?, // -1
    @SerializedName("pricePerServing")
    val pricePerServing: Double?, // 134.63
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int?, // 45
    @SerializedName("servings")
    val servings: Int?, // 6
    @SerializedName("sourceName")
    val sourceName: String?, // blogspot.com
    @SerializedName("sourceUrl")
    val sourceUrl: String?, // http://foodandspice.blogspot.com/2016/05/cannellini-bean-and-asparagus-salad.html
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String?, // https://spoonacular.com/cannellini-bean-and-asparagus-salad-with-mushrooms-782585
    @SerializedName("summary")
    val summary: String?, // Cannellini Bean and Asparagus Salad with Mushrooms requires approximately <b>45 minutes</b> from start to finish. This main course has <b>482 calories</b>, <b>31g of protein</b>, and <b>6g of fat</b> per serving. This gluten free, dairy free, lacto ovo vegetarian, and vegan recipe serves 6 and costs <b>$1.35 per serving</b>. 309 people were impressed by this recipe. Head to the store and pick up grain mustard, sea salt, lemon zest, and a few other things to make it today. It is brought to you by foodandspice.blogspot.com. Taking all factors into account, this recipe <b>earns a spoonacular score of 70%</b>, which is pretty good. Similar recipes are <a href="https://spoonacular.com/recipes/cannellini-bean-salad-422994">Cannellini Bean Salad</a>, <a href="https://spoonacular.com/recipes/refreshing-cannellini-bean-salad-113127">Refreshing Cannellini Bean Salad</a>, and <a href="https://spoonacular.com/recipes/cannellini-and-green-bean-salad-33177">Cannellini-and-Green Bean Salad</a>.
    @SerializedName("sustainable")
    val sustainable: Boolean?, // false
    @SerializedName("title")
    val title: String?, // Cannellini Bean and Asparagus Salad with Mushrooms
    @SerializedName("vegan")
    val vegan: Boolean?, // true
    @SerializedName("vegetarian")
    val vegetarian: Boolean?, // true
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean?, // true
    @SerializedName("veryPopular")
    val veryPopular: Boolean?, // false
    @SerializedName("weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int?, // 12
    @SerializedName("winePairing")
    val winePairing: WinePairing?
) {
    @Parcelize
    data class AnalyzedInstruction(
        @SerializedName("name")
        val name: String?,
        @SerializedName("steps")
        val steps: @RawValue List<Step>?
    ) : Parcelable {
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
            val step: String? // Rinse the cannellini beans and soak for 8 hours or overnight in several inches of water.
        ) {
            data class Equipment(
                @SerializedName("id")
                val id: Int?, // 404669
                @SerializedName("image")
                val image: String?, // sauce-pan.jpg
                @SerializedName("localizedName")
                val localizedName: String?, // sauce pan
                @SerializedName("name")
                val name: String? // sauce pan
            )

            data class Ingredient(
                @SerializedName("id")
                val id: Int?, // 10716050
                @SerializedName("image")
                val image: String?, // cooked-cannellini-beans.png
                @SerializedName("localizedName")
                val localizedName: String?, // cannellini beans
                @SerializedName("name")
                val name: String? // cannellini beans
            )

            data class Length(
                @SerializedName("number")
                val number: Int?, // 480
                @SerializedName("unit")
                val unit: String? // minutes
            )
        }
    }

    data class ExtendedIngredient(
        @SerializedName("aisle")
        val aisle: String?, // Pasta and Rice;Canned and Jarred
        @SerializedName("amount")
        val amount: Double?, // 3.75
        @SerializedName("consistency")
        val consistency: String?, // SOLID
        @SerializedName("id")
        val id: Int?, // 10016049
        @SerializedName("image")
        val image: String?, // dry-cannellini-beans.jpg
        @SerializedName("measures")
        val measures: Measures?,
        @SerializedName("meta")
        val meta: List<String?>?,
        @SerializedName("name")
        val name: String?, // cannellini beans
        @SerializedName("nameClean")
        val nameClean: String?, // dried cannellini beans
        @SerializedName("original")
        val original: String?, // 1 1/4 cups dried cannellini (white kidney) beans (3 3/4 cups cooked)
        @SerializedName("originalName")
        val originalName: String?, // 1/4 cups dried cannellini (white kidney) beans cooked)
        @SerializedName("unit")
        val unit: String? // cups
    ) {
        data class Measures(
            @SerializedName("metric")
            val metric: Metric?,
            @SerializedName("us")
            val us: Us?
        ) {
            data class Metric(
                @SerializedName("amount")
                val amount: Double?, // 887.205
                @SerializedName("unitLong")
                val unitLong: String?, // milliliters
                @SerializedName("unitShort")
                val unitShort: String? // ml
            )

            data class Us(
                @SerializedName("amount")
                val amount: Double?, // 3.75
                @SerializedName("unitLong")
                val unitLong: String?, // cups
                @SerializedName("unitShort")
                val unitShort: String? // cups
            )
        }
    }

    data class WinePairing(
        @SerializedName("pairedWines")
        val pairedWines: List<String?>?,
        @SerializedName("pairingText")
        val pairingText: String?, // Salad on the menu? Try pairing with Chardonnay, Sauvignon Blanc, and Gruener Veltliner. Sauvignon Blanc and Gruner Veltliner both have herby notes that complement salads with enough acid to match tart vinaigrettes, while a Chardonnay can be a good pick for creamy salad dressings. One wine you could try is Tenuta di Nozzole Le Bruniche Chardonnay. It has 4.4 out of 5 stars and a bottle costs about 12 dollars.
        @SerializedName("productMatches")
        val productMatches: List<ProductMatche?>?
    ) {
        data class ProductMatche(
            @SerializedName("averageRating")
            val averageRating: Double?, // 0.8799999952316284
            @SerializedName("description")
            val description: String?, // Nozzole Le Bruniche is an elegant and distinctive expression of the Chardonnay variety, with a clean, fragrant bouquet of white and tropical fruits offset by slight nuances of toast. On the palate, it shows a superb balance of fruit ripeness underscored by a fresh acidity and structure of medium body, with an overall impression of delicate complexity. These are repeated in the clean, persistent finish, which ends on a subtle toasty note.
            @SerializedName("id")
            val id: Int?, // 436516
            @SerializedName("imageUrl")
            val imageUrl: String?, // https://spoonacular.com/productImages/436516-312x231.jpg
            @SerializedName("link")
            val link: String?, // https://click.linksynergy.com/deeplink?id=*QCiIS6t4gA&mid=2025&murl=https%3A%2F%2Fwww.wine.com%2Fproduct%2Ftenuta-di-nozzole-le-bruniche-chardonnay-1999%2F27974
            @SerializedName("price")
            val price: String?, // $11.989999771118164
            @SerializedName("ratingCount")
            val ratingCount: Double?, // 5.0
            @SerializedName("score")
            val score: Double?, // 0.8174999952316284
            @SerializedName("title")
            val title: String? // Tenuta di Nozzole Le Bruniche Chardonnay
        )
    }
}