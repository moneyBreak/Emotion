package com.example.gesang.emotion.injection.module

import android.app.Activity
import com.example.gesang.emotion.base.BaseMvpActivity
import com.example.gesang.emotion.base.BasePresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity:Activity) {

    @Provides
    fun providesActivity():Activity{
        return activity
    }
}