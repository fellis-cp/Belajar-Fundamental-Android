package com.dicoding.githubhanif.ui.setting

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.prefDataStore by preferencesDataStore("Setting")

class SettingPreferences constructor(context: Context) {
    private val settingDataStore = context.prefDataStore
    private val temaKEY = booleanPreferencesKey("theme_setting")


    fun getTema(): Flow<Boolean> =
        settingDataStore.data.map {  preferences ->
            preferences[temaKEY]?: false
        }

    suspend fun saveTema(
        ismodeMalamActive: Boolean
    ) {
        settingDataStore.edit { preferences ->
            preferences[temaKEY] = ismodeMalamActive

        }
    }
}