package com.example.recipe.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipe.data.model.register.BodyRegister
import com.example.recipe.data.model.register.DataStoreUser
import com.example.recipe.data.source.RemoteSource
import com.example.recipe.utils.REGISTER_HASH
import com.example.recipe.utils.REGISTER_INFO
import com.example.recipe.utils.REGISTER_USERNAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class RegisterRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remote: RemoteSource) {

    //DataStore
    private object storeKeys{
        val username = stringPreferencesKey(REGISTER_USERNAME)
        val hash = stringPreferencesKey(REGISTER_HASH)
    }

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(REGISTER_INFO)

    suspend fun saveDataRegister(username: String, hash:String) {
        context.dataStore.edit {
            it[storeKeys.username] = username
            it[storeKeys.hash] = hash
        }
    }

    val readDataRegister : Flow<DataStoreUser> = context.dataStore.data
        .catch { e ->
            if (e is IOException)
                emit(emptyPreferences())
            else
                throw e
        }
        .map {
            val username = it[storeKeys.username] ?: ""
            val hash = it[storeKeys.hash] ?: ""
            DataStoreUser(username, hash)
        }

    //API
    suspend fun postRegister(apiKey: String, body: BodyRegister) = remote.postRegister(apiKey,body)

}