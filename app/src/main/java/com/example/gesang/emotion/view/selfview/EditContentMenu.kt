package com.example.gesang.emotion.view.selfview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.PopupWindow
import com.example.gesang.emotion.R
import com.example.gesang.emotion.view.adapter.MyListAdapter


class EditContentMenu(context: Context?,layout:Int?,width:Int?,height:Int?) {

    private val context = context
    private val contentView:View
    var mEditList:ListView
//    private val listAdapter = MyListAdapter(context, 2)//这里不设置adapter，在activity里面设置
    val listPop: PopupWindow

    init {
        contentView = inflatView(context!!)
        initView()
        initEvent()
        mEditList = initListView()
//        mEditList.adapter = listAdapter

        listPop = PopupWindow(contentView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true)
        //点击接口回调!!!

        //initWindow()
    }

//    fun initWindow(){
//        listPop.setOnDismissListener(PopupWindow.OnDismissListener {
//            val lp = context.getWindow().getAttributes()
//            lp.alpha = 1.0f
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//            getWindow().setAttributes(lp)
//        })
//    }

    fun initEvent(){
        mEditList.onItemClickListener = AdapterView.OnItemClickListener() {
            parent, view, position, id ->
            listPop.dismiss()
            //getId???
        }
    }

     fun initView(){
         mEditList = contentView.findViewById(R.id.popList)
     }

    private fun initListView()= contentView.findViewById<ListView>(R.id.popList)

    private fun inflatView(context: Context)=LayoutInflater.from(context).inflate(R.layout.edit_content_popupwindow_layout,null,false)

    fun showAtLocation(parent: View, gravity: Int, x: Int, y: Int) {
        listPop.showAtLocation(parent, gravity, x, y)
    }

    fun showAsDropDown(anchor: View, x: Int, y: Int,gravity: Int){
        listPop.showAsDropDown(anchor,x,y,gravity)
    }
}