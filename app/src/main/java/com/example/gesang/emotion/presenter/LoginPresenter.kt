package com.example.gesang.emotion.presenter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.example.gesang.emotion.base.BasePresenter
import com.example.gesang.emotion.model.data.User
import com.example.gesang.emotion.presenter.view.LoginView
import com.example.gesang.emotion.service.i.UserService
import com.example.gesang.emotion.util.BaseObserver
import com.example.gesang.emotion.util.execute
import javax.inject.Inject

class LoginPresenter @Inject constructor(): BasePresenter<LoginView>(){

    @Inject
    lateinit var userService:UserService

    fun login(username: String,password: String,owner:LifecycleOwner){
        userService.login(username,password).execute(object : BaseObserver<User>(){
            override fun onNext(t: User) {
                Log.e("PresenterResult:",t.toString())
                mView.onLoginReturn(t)
            }
        },owner)
    }

}