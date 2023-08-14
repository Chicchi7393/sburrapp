package me.chicchi7393.sburrapp.helpers

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.chicchi7393.sburrapp.dataStore

class DatastoreHelper(private val context: Context) {

    fun getDeviceId(): Flow<String?> {
        val usernameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
            preferences[stringPreferencesKey("deviceid")]
        }

        return usernameFlow
    }

    suspend fun setDeviceId(deviceId: String) {
        context.dataStore.edit { settings ->
            settings[stringPreferencesKey("deviceid")] = deviceId
        }
    }

}
