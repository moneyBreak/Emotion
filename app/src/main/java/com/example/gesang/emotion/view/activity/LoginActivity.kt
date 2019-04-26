package com.example.gesang.emotion.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import com.example.gesang.emotion.R
import com.example.gesang.emotion.base.BaseMvpActivity
import com.example.gesang.emotion.base.BaseView
import com.example.gesang.emotion.injection.component.DaggerUserComponent
import com.example.gesang.emotion.injection.module.UserModule
import com.example.gesang.emotion.model.data.User
import com.example.gesang.emotion.presenter.LoginPresenter
import com.example.gesang.emotion.presenter.RegisterPresenter
import com.example.gesang.emotion.presenter.view.LoginView
import kotlinx.android.synthetic.main.login_layout.*
import javax.inject.Inject

class LoginActivity : BaseMvpActivity<LoginPresenter>(), LoginView {

    override fun onLoginReturn(user: User) {
        showToast(this,"这里回调成功了")
    }

//    @Inject
//    lateinit var userInfo:User

    override fun injectComponent() {

        DaggerUserComponent.builder().activityComponent(activityComponent).userModule(UserModule()).build().inject(this)
        mPresenter.mView = this

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        mRegisterButton.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        mLoginButton.setOnClickListener {
            val username ="[money]"
            val password = "[123456]"
            mPresenter.login(username,password,this)
        }
    }
}