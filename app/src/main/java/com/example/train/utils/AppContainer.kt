package com.example.train.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.preferencesDataStore

class AppContainer {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val USER_DATA_STORE_FILE_NAME = "user_store.pb"
        const val USER_PREFERENCES_NAME = "user_store"
    }

}