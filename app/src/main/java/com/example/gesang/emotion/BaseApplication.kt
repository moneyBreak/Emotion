package com.example.gesang.emotion

import android.app.Application
import com.example.gesang.emotion.injection.component.AppComponent
import com.example.gesang.emotion.injection.component.DaggerAppComponent
import com.example.gesang.emotion.injection.module.AppModule
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility
import com.tencent.mmkv.MMKV


class BaseApplication: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        initInjection()

        val rootDir = MMKV.initialize(this)
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5caf3aa9")

    }

    private fun initInjection() {

        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()

    }

}