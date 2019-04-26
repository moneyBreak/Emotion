package com.example.gesang.emotion.presenter

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.gesang.emotion.base.BasePresenter
import com.example.gesang.emotion.presenter.view.RegisterView
import com.example.gesang.emotion.service.i.UserService
import com.example.gesang.emotion.util.BaseObserver
import com.example.gesang.emotion.util.execute
import javax.inject.Inject

class RegisterPresenter @Inject constructor():BasePresenter<RegisterView>(){

    @Inject
    lateinit var userService:UserService

    @SuppressLint("CheckResult")
    fun register(username:String, password:String,owner: LifecycleOwner){
        //业务逻辑
        userService.register(username,password).execute(object :BaseObserver<String>(){
            override fun onNext(t: String) {
                Log.e("PresenterResult:",t)
                mView.onRegisterResult(t)
            }
        },owner)
    }


}

