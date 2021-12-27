package com.example.train.repo

import com.example.train.remote.ClientApi

class LoginRepository(
    private val api: ClientApi
) : BaseRepository() {

    suspend fun userInfo(username:String) = safeApi {
        api.userInfo(username)
    }

}