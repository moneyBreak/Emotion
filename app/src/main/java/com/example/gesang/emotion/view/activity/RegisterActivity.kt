package com.example.gesang.emotion.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gesang.emotion.R
import com.example.gesang.emotion.base.BaseMvpActivity
import com.example.gesang.emotion.injection.component.DaggerUserComponent
import com.example.gesang.emotion.injection.module.UserModule
import com.example.gesang.emotion.presenter.RegisterPresenter
import com.example.gesang.emotion.presenter.view.RegisterView
import kotlinx.android.synthetic.main.login_layout.*

class RegisterActivity: BaseMvpActivity<RegisterPresenter>(),RegisterView {

    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this
    }

    /*
    注册方法
     */
    override fun onRegisterResult(result: String) {
        showToast(this,result)
//        startActivity(Intent(this,MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.register_layout)

        mRegisterButton.setOnClickListener {
//            val username = mRegisterUserName.editText!!.text.toString()
//            val userpass = mRegisterUserPass.editText!!.text.toString()
            val username ="[money]"
            val password = "[123456]"

            mPresenter.register(username,password,this)

        }

    }

}