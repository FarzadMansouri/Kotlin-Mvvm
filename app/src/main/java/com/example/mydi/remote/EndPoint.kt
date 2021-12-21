package com.example.mydi.remote

import retrofit2.http.GET

interface EndPoint {



    @GET("users/{username}")



}