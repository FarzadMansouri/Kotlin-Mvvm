package com.example.train.repo

class LoginRepository(
    private val api: com.example.train.remote.ClientApi
) : BaseRepository() {

    suspend fun userInfo(username:String) = safeApi {
        api.userInfo(username)
    }

}