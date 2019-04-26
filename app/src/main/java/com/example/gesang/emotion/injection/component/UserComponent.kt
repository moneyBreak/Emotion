package com.example.gesang.emotion.injection.component

import com.example.gesang.emotion.injection.module.UserModule
import com.example.gesang.emotion.injection.scope.PerComponentScope
import com.example.gesang.emotion.view.activity.LoginActivity
import com.example.gesang.emotion.view.activity.RegisterActivity
import dagger.Component

@PerComponentScope
@Component(modules = [UserModule::class] ,dependencies = [ActivityComponent::class])
interface UserComponent {

    fun inject(activity:RegisterActivity)

    fun inject(activity: LoginActivity)

}