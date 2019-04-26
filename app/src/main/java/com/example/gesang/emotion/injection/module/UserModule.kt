package com.example.gesang.emotion.injection.module

import com.example.gesang.emotion.service.i.UserService
import com.example.gesang.emotion.service.impl.UserServiceImpl
import dagger.Module
import dagger.Provides

@Module
class UserModule {
    @Provides
    fun providesUserService(service:UserServiceImpl):UserService{
        return service
    }
}