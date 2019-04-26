package com.example.gesang.emotion.view.adapter
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.gesang.emotion.R
import com.example.gesang.emotion.view.selfview.ListHolder


/*
 * 个人设置的adapter
 */

const val PERSONAL_LIST = 0
const val LABEL_LIST = 1
const val EDIT_BOTTOOM_LEFT_LIST = 2
const val EDIT_BOTTOM_RIGHT_LIST = 3

class MyListAdapter(context:Context?,type:Int): BaseAdapter() {

    private val mPersonList = arrayListOf("记事", "备忘", "归档存储", "回收站")
    private val mIconPersonList = arrayListOf( R.drawable.icon_memorry,R.drawable.icon_memo,
        R.drawable.cloud_storage,R.drawable.icon_trash )

    private val mLabelList = arrayListOf("标签1","hahaha","今儿爷心情好","太能花钱了","心情不好专用标签",
            "再往下就是测试滚动了","我也不知道这个能不能滚动","只能姑且试一试了","这是个正经的标签")

    private val mEditContentMenu = arrayListOf("图片","转译","录音")
    private val mIconContentMenu = arrayListOf(R.drawable.icon_inner_pic_24dp,R.drawable.icon_transform,R.drawable.ic_record)

    private val mEditChoiceMenu = arrayListOf("删除","标签")
    private val mIconChoiceMenu = arrayListOf(R.drawable.icon_remove,R.drawable.icon_label)

    private val mInflater : LayoutInflater = LayoutInflater.from(context)
    private val type = type

    private var viewHolder : ListHolder?=null
//    private var selected:Int?= null

    @SuppressLint("ResourceAsColor")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        convertView?.let {
            viewHolder = convertView.tag as ListHolder?
        }

        var v = convertView
        if(v === null){
            v =mInflater.inflate(R.layout.left_list_item,parent,false)
            viewHolder = ListHolder(v)
            v.tag = viewHolder
        }

        when(type){
            PERSONAL_LIST -> {
                viewHolder!!.mLeftIcon.setImageResource(mIconPersonList[position])
                viewHolder!!.mMenuTitle.text = mPersonList[position]
            }
            LABEL_LIST -> {
                viewHolder!!.mLeftIcon.setImageResource(R.drawable.icon_label)
                viewHolder!!.mMenuTitle.text = mLabelList[position]
            }
            EDIT_BOTTOOM_LEFT_LIST -> {
                viewHolder!!.mLeftIcon.setImageResource(mIconContentMenu[position])
                viewHolder!!.mMenuTitle.text = mEditContentMenu[position]
            }
            EDIT_BOTTOM_RIGHT_LIST ->{
                viewHolder!!.mLeftIcon.setImageResource(mIconChoiceMenu[position])
                viewHolder!!.mMenuTitle.text = mEditChoiceMenu[position]
            }
        }

        return v!!
    }

    override fun getItem(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount()=when(type){
        PERSONAL_LIST ->mPersonList.size
        LABEL_LIST ->mLabelList.size
        EDIT_BOTTOOM_LEFT_LIST ->mEditContentMenu.size
        EDIT_BOTTOM_RIGHT_LIST ->mEditChoiceMenu.size
        else -> mPersonList.size
    }

   // fun getTitle(position: Int) = mPersonList[position]

            //fun setSelected(position: Int) = position

}