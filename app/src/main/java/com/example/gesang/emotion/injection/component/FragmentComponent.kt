package com.example.gesang.emotion.injection.component

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.gesang.emotion.injection.module.FragmentModule
import com.example.gesang.emotion.injection.scope.FragmentScope

import dagger.Component


@FragmentScope
@Component(modules = [FragmentModule::class],dependencies = [ActivityComponent::class])
interface FragmentComponent {

    fun activity(): Activity

    fun fragment():Fragment

//    fun lifecycleProvider():LifecycleProvider<*>//???

}