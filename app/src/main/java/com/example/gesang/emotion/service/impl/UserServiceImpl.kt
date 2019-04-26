package com.example.gesang.emotion.service.impl

import android.util.Log
import com.example.gesang.emotion.model.Exception.BaseException
import com.example.gesang.emotion.model.data.User
import com.example.gesang.emotion.model.remote.BaseResponse
import com.example.gesang.emotion.model.repository.UserRepository
import com.example.gesang.emotion.service.i.UserService
import com.github.salomonbrys.kotson.fromJson
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.functions.Function
import okhttp3.logging.HttpLoggingInterceptor
import java.lang.Exception
import java.net.HttpCookie
import javax.inject.Inject

class UserServiceImpl @Inject constructor(): UserService {


    @Inject
    lateinit var repository:UserRepository

    override fun register(username: String, password: String): Observable<String> {

        return repository.register(username, password).flatMap (object :Function<BaseResponse<String>,Observable<String>>{
            override fun apply(t: BaseResponse<String>): Observable<String> {

//                Log.e("t.data:",t.data)
//                if(t.status != 0){
//                    return Observable.error(BaseException(t.status,t.msg))
//                }
//
                when(t.status){
                    0 -> return Observable.just("注册成功！")
                    1 -> return Observable.just("账号已存在，请返回登录")
                    2 -> return Observable.just("登录失败，未能链接服务器")
                }
                return Observable.just("未知错误")
            }
        })
    }


    override fun login(username: String, password: String): Observable<User> {
        return repository.login(username,password).flatMap (object :Function<BaseResponse<String>,Observable<User>>{
            override fun apply(t: BaseResponse<String>): Observable<User> {
                Log.e("response:",t.data)
                var result :User?=null
                if(t.status == 0){

                    try {
                        val user:User = Gson().fromJson(t.data)
                        result = user
                    }catch (e:Exception){
                        println(e)
                    }
                    Log.e("userInfo:",result.toString())

//                val list = Gson().fromJson<>()

                    return Observable.just(result!!)
                }
                return Observable.just(result)


            }

        })

    }
}