package me.chicchi7393.sburrapp.helpers

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import me.chicchi7393.sburrapp.dataStore

class DatastoreHelper(private val context: Context) {
    fun getUsername(): Flow<String?> {
        val usernameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("username")]
        }

        return usernameFlow
    }

    suspend fun setUsername(username: String) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey("username")] = username
        }
    }

}
