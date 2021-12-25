package com.example.train.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ClientApi {


    @GET("users/{username}")
    suspend fun userInfo(@Path("username") username: String): com.example.train.model.UserDataModel



}