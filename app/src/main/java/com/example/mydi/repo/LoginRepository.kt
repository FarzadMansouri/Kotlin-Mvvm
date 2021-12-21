package com.example.mydi.repo

import com.example.mydi.datasource.UserLocalDataSource
import com.example.mydi.datasource.UserRemoteDataSource
import com.example.mydi.remote.EndPoint

class LoginRepository(
    val endPoint: EndPoint
) : BaseRepository() {


    val loginUser=safeApi {

    }

}