package com.example.gesang.emotion.injection.module

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides


@Module
class FragmentModule(private val fragment:Fragment) {

    @Provides
    fun providesFragment():Fragment{
        return fragment
    }
}