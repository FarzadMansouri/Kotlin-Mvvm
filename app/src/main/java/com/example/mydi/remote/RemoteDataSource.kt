package com.example.mydi.remote

import com.example.mydi.utils.AppContainer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RemoteDataSource {


    private val client = OkHttpClient.Builder().also {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        it.addInterceptor(logger)
    }.build()

    fun <Api> makeApi(api: Class<Api>): Api {
        return Retrofit.Builder()
            .baseUrl(AppContainer.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(api)

    }


}