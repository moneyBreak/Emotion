package com.example.gesang.emotion.injection.component

import com.example.gesang.emotion.injection.module.NoteModule
import com.example.gesang.emotion.injection.scope.PerComponentScope
import com.example.gesang.emotion.view.fragment.WaterfallFragment
import dagger.Component

@PerComponentScope
@Component(modules = [NoteModule::class],dependencies = [ActivityComponent::class])
interface NoteComponent {

    fun inject(fragment: WaterfallFragment)

}