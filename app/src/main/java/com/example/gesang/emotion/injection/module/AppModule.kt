package com.example.gesang.emotion.injection.module

import android.content.Context
import com.example.gesang.emotion.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (private val context:BaseApplication){

    @Provides
    @Singleton
    fun providesContext():Context{
        return context
    }
}