package com.example.gesang.emotion.model.i

import com.example.gesang.emotion.model.data.User
import com.example.gesang.emotion.model.remote.BaseResponse
import com.example.gesang.emotion.model.request.LoginRequest
import com.example.gesang.emotion.model.request.RegisterRequest
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/*
请求接口 以及接收内容的返回类型
 */

interface UserApi {

    @POST("api/register")
    fun register(@Body request: RegisterRequest):Observable<BaseResponse<String>>

    @POST("api/v1/login")
    fun login(@Body request: LoginRequest):Observable<BaseResponse<String>>

}