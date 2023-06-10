package com.example.recipe.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipe.data.model.menu.MenuStoreModel
import com.example.recipe.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class MenuRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object storedKey{
        val mealTitle = stringPreferencesKey(MENU_MEAL_TITLE)
        val mealId = intPreferencesKey(MENU_MEAL_ID)
        val dietTitle = stringPreferencesKey(MENU_DIET_TITLE)
        val dietId = intPreferencesKey(MENU_DIET_ID)
    }

    private val Context.datastore : DataStore<Preferences> by preferencesDataStore(MENU_DATASTORE)

    suspend fun saveMenuData(mealTitle: String, mealId: Int, dietTitle: String, dietId: Int){
        context.datastore.edit {
            it[storedKey.mealTitle] = mealTitle
            it[storedKey.mealId] = mealId
            it[storedKey.dietTitle] = dietTitle
            it[storedKey.dietId] = dietId

        }
    }

    val readMenuData : Flow<MenuStoreModel> = context.datastore.data.
        catch {e->
            if (e is IOException) emit(emptyPreferences())
            else throw e
    }.map {
        val meal = it[storedKey.mealTitle]?: MAIN_COURSE
        val mealId = it[storedKey.mealId]?: 0
        val diet = it[storedKey.dietTitle]?: GLUTEN_FREE
        val dietId = it[storedKey.dietId]?: 0
        MenuStoreModel(meal, mealId, diet, dietId)
    }
}