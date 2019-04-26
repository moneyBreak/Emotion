package com.example.gesang.emotion.base

import javax.inject.Inject

open class BasePresenter<T:BaseView> {

    lateinit var mView:T

}