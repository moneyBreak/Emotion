package com.example.gesang.emotion.model.repository

import com.example.gesang.emotion.model.i.UserApi
import com.example.gesang.emotion.model.remote.BaseResponse
import com.example.gesang.emotion.model.remote.RetrofitFactory
import com.example.gesang.emotion.model.request.LoginRequest
import com.example.gesang.emotion.model.request.RegisterRequest
import io.reactivex.Observable
import javax.inject.Inject

/*
真正的访问网络的方法
 */
class UserRepository @Inject constructor(){

    fun register(username:String,password:String):Observable<BaseResponse<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .register(RegisterRequest(username, password))
    }

    fun login(username:String,password:String):Observable<BaseResponse<String>>{
        return RetrofitFactory.instance.create(UserApi::class.java)
                .login(LoginRequest(username, password))
    }

}