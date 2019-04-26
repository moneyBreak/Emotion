package com.example.gesang.emotion.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.example.gesang.emotion.BaseApplication
import com.example.gesang.emotion.injection.component.ActivityComponent
import com.example.gesang.emotion.injection.component.DaggerActivityComponent
import com.example.gesang.emotion.injection.module.ActivityModule
import com.example.gesang.emotion.model.data.Note
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import com.tencent.mmkv.MMKV
import javax.inject.Inject

abstract class BaseMvpActivity<P :BasePresenter<*>>: BaseActivity(),BaseView{

    lateinit var activityComponent:ActivityComponent

    var kv = MMKV.defaultMMKV()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivityInjection()
        injectComponent()
        makeDataList()
    }
    abstract fun injectComponent()

    private fun initActivityInjection() {
        activityComponent = DaggerActivityComponent.builder().appComponent((application as BaseApplication).appComponent)
                .activityModule(ActivityModule(this))
                .build()
    }

    fun showToast(context: Context,str:String ){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
    }

    @Inject
    lateinit var mPresenter:P

    val dataList : List<Note>
        get() {
            return arrayListOf(
                    Note(birth = "2019.4.1",title = "我终于还是写了Note数据类",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.2",title = "虽然我很不情愿",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.3",title = "对于我自己的",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.4",title = "对于毕业设计我心理没底",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.5",title = "但是又不能不写",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.6",title = "不然就要掏钱了",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.7",title = "自我安慰吧",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.8",title = "完成毕设也是再赚钱",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.9",title = "虽然赚的不是很多",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧"),
                    Note(birth = "2019.4.10",title = "但是聊胜于无",content = "我这就是一段测试得内容，现在只能实现单个图片得加载，如果可以我希望携程多个图片的加载，这样可以吧")
            )
        }

    fun keyList(): MutableSet<String> {
        var result = mutableSetOf<String>()
        for(note in dataList){
            result.add(note.birth!!)
        }
        return result
    }

    fun getKeySet()= keyList()

    fun makeDataList(){

        var set = mutableSetOf<String>()

        for(data in dataList){
            val obj :JsonObject = jsonObject(
                    "title" to data.title,
                    "birth" to data.birth,
                    "date" to data.date,
                    "lastDate" to data.lastDate,
                    "imageUrl" to data.imageUrl,
                    "imageUrl" to data.record,
                    "content" to data.content
            )

            set.add(obj.asString)
        }
        kv.encode("notes",set)
    }
}