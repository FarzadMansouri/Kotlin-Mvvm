package com.example.mydi.repo

import com.example.mydi.remote.ClientApi

class LoginRepository(
    private val api: ClientApi
) : BaseRepository() {

    suspend fun userInfo(username:String) = safeApi {
        api.userInfo(username)
    }

}