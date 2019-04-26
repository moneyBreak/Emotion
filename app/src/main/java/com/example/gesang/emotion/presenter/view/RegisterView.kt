package com.example.gesang.emotion.presenter.view

import com.example.gesang.emotion.base.BaseView

/*
view层面的接口回调
 */
interface RegisterView:BaseView {

    fun onRegisterResult(result: String)

}