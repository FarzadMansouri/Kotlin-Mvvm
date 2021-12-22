package com.example.mydi.remote

import com.example.mydi.model.UserDataModel
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClientApi {


    @GET("users/{username}")
    suspend fun userInfo(@Path("username") username: String):UserDataModel



}