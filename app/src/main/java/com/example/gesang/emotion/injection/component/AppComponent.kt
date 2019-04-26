package com.example.gesang.emotion.injection.component

import android.content.Context
import com.example.gesang.emotion.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun context():Context
}