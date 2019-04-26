package com.example.gesang.emotion.model.remote

import android.util.Log
import com.tencent.mmkv.MMKV
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val cookie:String = "Set-Cookie"
class RetrofitFactory private constructor() {

    companion  object {
        val instance :RetrofitFactory by lazy { RetrofitFactory() }
    }

    private val retrofit: Retrofit
    //头部拦截器
    private val interceptor: Interceptor


    private val kv = MMKV.defaultMMKV()

    init {

        interceptor = Interceptor { chain ->
            val request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("charset", "utf-8")
                    .addHeader("Cookie",getCookie())//煞笔添加方式
                    .addHeader("Connection","close")
                    .build()
            chain.proceed(request)
        }



        retrofit = Retrofit.Builder()
                .baseUrl(BaseConstant.SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build()


    }


    private fun initClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(initLogInterceptor())
                .addInterceptor(CookieGetInterceptor())
//                .addInterceptor(CookieSetInterceptor())
                .connectTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build()

    }
/*
这是一个日志拦截器
 */
    private fun initLogInterceptor(): Interceptor {

        val interceptor = HttpLoggingInterceptor()

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor

    }
/*
拦截Cookie的拦截器
 */

    inner class CookieGetInterceptor:Interceptor{
        override fun intercept(chain: Interceptor.Chain?): Response? {
            val request = chain?.request()?:return null
            val response = chain.proceed(request)
            val cookies  = mutableSetOf<String>()
            response.headers("Set-Cookie").run {
                if (isNotEmpty()){
                    forEach {
                        cookies.add(it)
                        Log.e("Set-Cookie:",it)
                    }
                }
            }
            this@RetrofitFactory.kv.encode(cookie,cookies)

            return response
        }
    }

    /*
    添加Cookie的信息拦截器
     */
//    inner class CookieSetInterceptor():Interceptor{
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val builder: Request.Builder = chain.request().newBuilder()
//            var stringBuilder:StringBuilder ? = null
//            this@RetrofitFactory.kv.decodeString(cookie) //取出上一步中存储的Cookie
//                    .run {
//                        if (isNotEmpty()) {
//                            forEach {
//                                stringBuilder?.append(it)!!.append(";")
//                            }
//                        }else {
//                            kv.encode(cookie,"")
//                        }
//                        builder.header("Cookie", stringBuilder.toString())
//                    }
//            val request = builder.build()
//            return chain.proceed(request)
//        }
//
//    }

    fun <T>create(service:Class<T>):T {
        return retrofit.create(service)
    }

    private fun getCookie(): String {
        kv.decodeString(cookie).let {
            return it
        }
        return ""
    }



}