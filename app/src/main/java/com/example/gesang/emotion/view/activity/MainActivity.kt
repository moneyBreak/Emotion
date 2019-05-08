package com.example.gesang.emotion.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.gesang.emotion.view.selfview.WaterfullDecoration

import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.example.gesang.emotion.R
import com.example.gesang.emotion.base.BaseMvpActivity
import com.example.gesang.emotion.injection.component.ActivityComponent
//import com.example.gesang.emotion.injection.component.DaggerActivityComponent
//import com.example.gesang.emotion.injection.component.DaggerUserComponent
import com.example.gesang.emotion.injection.module.UserModule
import com.example.gesang.emotion.model.data.Note
import com.example.gesang.emotion.presenter.MainPresenter
import com.example.gesang.emotion.presenter.view.MainView
import com.example.gesang.emotion.view.adapter.MyListAdapter

import com.example.gesang.emotion.view.i.IHeaderBar
import com.example.gesang.emotion.view.adapter.OnCardClickListener
import com.example.gesang.emotion.view.adapter.WaterfallAdapter
import com.example.gesang.emotion.view.fragment.WaterfallFragment
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject

import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.header_layout.*
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity: AppCompatActivity() {
//    lateinit var activityComponent: ActivityComponent

//    override fun injectComponent() {
//        activityComponent = DaggerActivityComponent.builder().appComponent()
//        mPresenter.mView = this
//    }


    private val context by lazy { this }
    private val layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

    private var listState = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //Naviagation有默认的关联
//        if(savedInstanceState == null){
//            val waterfall = WaterfallFragment()
//
//            waterfall.arguments = intent.extras
//            supportFragmentManager.beginTransaction()
//                    .add(R.id.my_nav_host_fragment,waterfall)
//                    .commit()
//        }

        initHeader()
        initDrawer()
        initRecycleList()
        initContent()
    }

    private fun initContent() {
        //可能需要在这一步 做一些加载的初始化操作
    }

    private fun initHeader() {
        headerBar.initClick(InitHeaderClass())
    }

    override fun onSupportNavigateUp() = findNavController(this,R.id.my_nav_host_fragment).navigateUp()

    inner class InitHeaderClass : IHeaderBar{
        override fun onClick(v: View?) {
            Log.e("click:","点击事件到这里了！！！")
            Log.e("v的类型:",v.toString())
            when(v){
                leftIcon -> {
                    mainLayout.openDrawer(drawerLeft)
                    Log.e("可以找到这个id:",v!!.id.toString())
                }
                rightOne -> {
                    //搜索
                }
                rightTwo -> {
                    if(listState){
                        rightTwo.setBackgroundResource(R.mipmap.icon_waterfall)
                        //渲染adapter为列表
                    }else{
                        rightTwo.setBackgroundResource(R.drawable.ic_dns_black_24dp)
                        //渲染adapter为瀑布流
                    }
                    listState = !listState

                }
            }
        }
    }

    private fun initRecycleList() {
        //设置渲染刷新动画
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recordRecyclerView.itemAnimator = null
        //设置两列的项目
        recordRecyclerView.layoutManager = layoutManager

        val adapter = WaterfallAdapter(this, dataList)
        adapter.onCardClickListener = CardClick()

        recordRecyclerView.adapter = adapter
        //设置列间隔和间隔
        val itemDecoration = WaterfullDecoration(this, 6)
        recordRecyclerView.addItemDecoration(itemDecoration)
    }

    inner class CardClick: OnCardClickListener {
        override fun onClick(v: View, position: Int) {
            val title=v.findViewById<View>(R.id.itemTitle)
            val contentText =v.findViewById<View>(R.id.itemContent)
            ///val image = v.findViewById<View>(R.id.itemImage)
//            val cardView = v.findViewById<View>(R.id.itemCard)
            val shareView = arrayOf(
                    Pair(contentText, ViewCompat.getTransitionName(contentText)),
                    Pair(title, ViewCompat.getTransitionName(title))
            )
            val intent = Intent(this@MainActivity, EditActivity::class.java)
            //可以从数据库请求一些数据
            //这里加入共享的View
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity,*shareView).toBundle()
//            intent.putExtra("position",position)
            val obj = toJsonString(dataList[position])
            intent.putExtra("note",obj)

            ActivityCompat.startActivity(this@MainActivity,intent,options)

        }
    }

//    override fun onSupportNavigateUp() =
//            findNavController(this, R.id.my_nav_host_fragment).navigateUp()

    @SuppressLint("ResourceAsColor")
    private fun initDrawer() {

        var kinds :String ?= null
        var lastClick = 0
        showHeaderImage()
        //这里可能还需要一个data
        val mPersonSettingAdapter = MyListAdapter(context, 0)
        personList.adapter = mPersonSettingAdapter
        val mLabelListAdapter = MyListAdapter(context, 1)
        labelList.adapter = mLabelListAdapter

        personList.setOnItemClickListener { parent, view, position, id ->


            //kinds = mPersonSettingAdapter.getTitle(position)//用来更新adapterList的渲染

            Log.e("lastIndex:",parent.indexOfChild(view).toString())
            Log.e("lastposition:",position.toString())
            Log.e("lastposition:",parent.getChildAt(position).toString())
        }
    }

    private fun showHeaderImage() {
        leftIcon.tag=null
        Glide.with(this).load(R.mipmap.header)
                .circleCrop()
                .into(leftHeader)
    }

    //    override fun injectComponent() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

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

    fun toJsonString(note :Note):String{

        val obj : JsonObject = jsonObject(
                "title" to note.title,
                "birth" to note.birth,
                "date" to note.date,
                "lastDate" to note.lastDate,
                "imageUrl" to note.imageUrl,
                "imageUrl" to note.record,
                "content" to note.content
        )

        return obj.toString()

    }
}