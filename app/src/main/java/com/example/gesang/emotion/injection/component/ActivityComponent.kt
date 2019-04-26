package com.example.gesang.emotion.injection.component

import android.app.Activity
import android.content.Context
import com.example.gesang.emotion.injection.module.ActivityModule
import com.example.gesang.emotion.injection.scope.ActivityScope

import dagger.Component

@ActivityScope
@Component(modules = [ActivityModule::class],dependencies = [AppComponent::class])
interface ActivityComponent {

    fun activity():Activity

    fun context():Context

}