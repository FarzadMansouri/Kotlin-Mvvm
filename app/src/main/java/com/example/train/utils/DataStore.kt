package com.example.train.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.PreferencesProto
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import com.example.train.UserInfo
import com.example.train.model.UserDataModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream
import java.net.UnknownServiceException

class DataStore(val context: Context) {

    companion object {
        const val USER_DATA_STORE_FILE_NAME = "user_info.pb"
        const val USER_PREFERENCES_NAME = "user_info_prefs"
        const val USER_SH_PREFERENCES_NAME = "user_info_shpf"
        private val USER_KEY = stringPreferencesKey("User_Info")
    }

    object UserInfoSerializer : Serializer<UserInfo> {
        override val defaultValue: UserInfo = UserInfo.getDefaultInstance()

        override suspend fun readFrom(input: InputStream): UserInfo {
            try {
                return UserInfo.parseFrom(input)
            } catch (ex: InvalidProtocolBufferException) {
                throw CorruptionException("Serializer Invalid", ex)
            }
        }

        override suspend fun writeTo(t: UserInfo, output: OutputStream) = t.writeTo(output)

    }

    val Context.userInfoProto: DataStore<UserInfo> by dataStore(
        USER_DATA_STORE_FILE_NAME,
        UserInfoSerializer
    )

    suspend fun saveUserInfoProto(value: UserDataModel) {
        context.userInfoProto.updateData {
            it.toBuilder()
                .setAvatarUrl(value.avatar_url)
                .setNodeId(value.node_id)
                .setBio(value.bio)
                .setCompany(value.company)
                .setId(value.id)
                .setLocation(value.location)
                .setLogin(value.login)
                .build()
        }
    }

    suspend fun readUserInfoProto(): UserInfo {
        return context.userInfoProto.data.first()
    }


    val Context.userInfoPrefs: DataStore<Preferences> by preferencesDataStore(USER_PREFERENCES_NAME)
    suspend fun saveUserInfoPrefs(value: UserDataModel) {
        val uInfo = Gson().toJson(value).toString()
        context.userInfoPrefs.edit { preferences ->
            preferences[USER_KEY] = uInfo
        }
    }

     fun readUserInfoPrefs(): Flow<String> {

        return context.userInfoPrefs.data.map {
            it[USER_KEY].toString()
        }

    }

}