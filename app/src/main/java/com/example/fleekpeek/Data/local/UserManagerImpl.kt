package com.example.fleekpeek.Data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.fleekpeek.Utils.Common
import com.example.fleekpeek.domain.manager.LocalManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManagerImpl(private val context: Context): LocalManager {

    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = Common.USER_SETTINGS)
    private object PreferenceKey {
        val APP_ENTRY = booleanPreferencesKey(name = Common.APP_ENTRY)
    }

    override suspend fun saveAppEntry() {
        context.dataStore.edit {
            settings ->
            settings[PreferenceKey.APP_ENTRY] = true
        }
    }

    override suspend fun readAppEntry(): Flow<Boolean> {
        return context.dataStore.data.map {
            it[PreferenceKey.APP_ENTRY]?:false
        }
    }



}