package com.example.gesang.emotion.base

import android.os.Bundle
import com.example.gesang.emotion.injection.component.DaggerFragmentComponent
import com.example.gesang.emotion.injection.component.FragmentComponent
import com.example.gesang.emotion.injection.module.FragmentModule
import javax.inject.Inject


abstract class BaseMvpFragment<P: BasePresenter<*>>: BaseFragment(),BaseView{

    lateinit var fragmentComponent: FragmentComponent

    @Inject
    lateinit var mPresenter:P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
    }
    abstract fun injectComponent()

    private fun initActivityInjection() {
//        fragmentComponent = DaggerFr.builder().appComponent((application as BaseApplication).appComponent)
//                .activityModule(ActivityModule(this))
//                .lifecycleProviderModule(LifecycleProviderModule(this))
//                .build()

        fragmentComponent = DaggerFragmentComponent.builder().activityComponent((activity as BaseMvpActivity<*>).activityComponent)
                .fragmentModule(FragmentModule(this))
                .build()


    }

}