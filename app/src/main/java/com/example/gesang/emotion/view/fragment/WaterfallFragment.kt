package com.example.gesang.emotion.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigator.Extras
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gesang.emotion.R
import com.example.gesang.emotion.base.BaseActivity
import com.example.gesang.emotion.base.BaseFragment
import com.example.gesang.emotion.base.BaseMvpActivity
import com.example.gesang.emotion.base.BaseMvpFragment
import com.example.gesang.emotion.injection.component.DaggerNoteComponent
import com.example.gesang.emotion.model.data.Note
import com.example.gesang.emotion.presenter.WaterfallPresenter
import com.example.gesang.emotion.presenter.view.WaterfallView
import com.example.gesang.emotion.view.activity.MainActivity
import com.example.gesang.emotion.view.adapter.OnCardClickListener
import com.example.gesang.emotion.view.adapter.WaterfallAdapter
import com.example.gesang.emotion.view.selfview.WaterfullDecoration
import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.waterfall_fragment_layout.*

class WaterfallFragment : Fragment(){
//    BaseMvpFragment<WaterfallPresenter>(),WaterfallView
//    override fun injectComponent() {
//
//    }

    val kv = MMKV.defaultMMKV()

    lateinit var mFragment:View

    private val layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.waterfall_fragment_layout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mFragment = view
        initRecycleList()
        makeDataList()

    }

    private fun initRecycleList() {

        //设置渲染刷新动画
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recordRecyclerView.itemAnimator = null
        //设置两列的项目
        recordRecyclerView.layoutManager = layoutManager

        val adapter = WaterfallAdapter(activity as MainActivity, dataList)
        adapter.onCardClickListener = CardClick()

        recordRecyclerView.adapter = adapter
        //设置列间隔和间隔
        val itemDecoration = WaterfullDecoration(activity as MainActivity, 6)
        recordRecyclerView.addItemDecoration(itemDecoration)

    }

    inner class CardClick: OnCardClickListener {
        override fun onClick(v: View, position: Int) {
            val title=v.findViewById<View>(R.id.itemTitle)
            val contentText =v.findViewById<View>(R.id.itemContent)

            val shareElements = mapOf(title to "title",
                    contentText to "contentText")

            val extras = FragmentNavigator.Extras.Builder()
                    .addSharedElements(shareElements).build()

            val obj = kv.decodeStringSet("notes").elementAt(position)

            val bundle = Bundle()
            bundle.putString("note",obj)

            Navigation.findNavController(mFragment).navigate(R.id.editInfoPage,bundle,null,extras)

        }
    }

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

            set.add(obj.toString())
        }
        kv.encode("notes",set)
    }


}