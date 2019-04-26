package com.example.gesang.emotion.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/*
继承监听生命周期的rxf，与presenter互动对象view,
因为和Navigation的fragment和RxFragment冲突，所以我们尝试着解决这个问题.
w我们集成让这个BaseFragment继承LifecycleProvider这个接口
 */
abstract class BaseFragment: Fragment(){


}