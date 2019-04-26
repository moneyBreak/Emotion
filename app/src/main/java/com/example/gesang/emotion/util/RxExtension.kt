package com.example.gesang.emotion.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.AutoDispose.autoDisposable
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.lifecycle.autoDisposable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.security.AccessControlContext
import java.util.*

open class BaseObserver<T>:Observer<T>{
    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
    }

    override fun onError(e: Throwable) {
    }

}

fun <T> Observable<T>.execute (observer: BaseObserver<T>,owner: LifecycleOwner){
    this.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .autoDisposable(AndroidLifecycleScopeProvider.from(owner, Lifecycle.Event.ON_DESTROY))//OnDestory时自动解绑
            .subscribe(observer)
}
