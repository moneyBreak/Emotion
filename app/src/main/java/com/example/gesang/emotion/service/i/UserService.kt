package com.example.gesang.emotion.service.i

import androidx.lifecycle.LifecycleOwner
import com.example.gesang.emotion.model.data.User
import io.reactivex.Observable


interface UserService {

    fun register(username:String,password:String):Observable<String>

    fun login(username:String,password:String):Observable<User>

}