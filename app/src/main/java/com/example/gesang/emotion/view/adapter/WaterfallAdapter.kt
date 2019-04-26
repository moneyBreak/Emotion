package com.example.gesang.emotion.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gesang.emotion.R
import com.example.gesang.emotion.model.data.Note
import com.example.gesang.emotion.view.activity.MainActivity
import kotlinx.android.synthetic.main.warterful_card_item.view.*

internal class WaterfallAdapter(activity: MainActivity,list: List<Note>): RecyclerView.Adapter<WaterfallViewHolder>() {

    private val activity = activity
//    private val dataList = dataList

    private val Title = "我就是标题"
    //private val contentList =context.resources.getStringArray(R.array.testList)

    var onCardClickListener : OnCardClickListener?= null

    private val imageList = arrayListOf(
            R.mipmap.temp1,R.mipmap.temp2,R.mipmap.temp3,R.mipmap.temp4,R.mipmap.temp5,R.mipmap.temp6,
            R.mipmap.temp7,R.mipmap.temp8,R.mipmap.temp9,R.mipmap.temp10
    )
    private var dataList = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaterfallViewHolder {
        val view =LayoutInflater.from(activity).inflate(R.layout.warterful_card_item,parent,false)
        return WaterfallViewHolder(view)
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: WaterfallViewHolder, position: Int) {
        //glide加载
        Glide.with(activity).load(imageList[position]).fitCenter().into(holder.itemImage)
        holder.itemContent.text = dataList[position].content
        holder.itemTitle.text = dataList[position].title

        //设置监听
        val itemView = holder.itemCard
        itemView.setOnClickListener {
            onCardClickListener.let{onCardClickListener!!.onClick(itemView,position)}
        }
    }
}
internal class WaterfallViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val itemImage = itemView.itemImage!!
    val itemTitle = itemView.itemTitle!!
    val itemContent = itemView.itemContent!!
    val itemBirth = itemView.itemBirth!!
    val itemCard = itemView.itemCard!!
}

interface OnCardClickListener{
    fun onClick(v:View,position:Int)
    //还可以加入长按
}