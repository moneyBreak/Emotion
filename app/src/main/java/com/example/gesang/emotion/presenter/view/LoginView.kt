package com.example.gesang.emotion.presenter.view

import com.example.gesang.emotion.base.BaseView
import com.example.gesang.emotion.model.data.User

interface LoginView :BaseView{

    fun onLoginReturn(user:User)
}