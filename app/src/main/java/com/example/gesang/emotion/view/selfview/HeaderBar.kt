package com.example.gesang.emotion.view.selfview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.example.gesang.emotion.R
import com.example.gesang.emotion.view.i.IHeaderBar

import kotlinx.android.synthetic.main.header_layout.view.*


const val HEADER_ICON = 0
const val BACK_ICON = 1
const val FINISH_ICON = 2

const val SEARCH_ICON = 0
const val TOP_ICON = 1
const val UNTOP_ICON = 2

const val TRANSFORM_LIST_ICON = 0
const val TRANSFORM_WARTERFUL_ICON = 1
const val TRANSFORM_MENU_ICON = 2
const val MEMO = 3
const val UNMEMO = 4

const val TABS_VISIBLE = true


class HeaderBar @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr){

//    private var mLeftIcon :Int
//    private var mTabs :Boolean
//    private var mRightOneIcon :Int
//    private var mRightTwoIcon :Int

    private var titles = arrayOf("昨日","今日","明日")

    private lateinit var mInflater: LayoutInflater

    private var headerView:View


    init {
//        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar)

//        mLeftIcon = typedArray.getInteger(R.styleable.HeaderBar_leftIcon, HEADER_ICON)
//        mTabs = typedArray.getBoolean(R.styleable.HeaderBar_Tabs, TABS_VISIBLE)
//        mRightOneIcon = typedArray.getInteger(R.styleable.HeaderBar_rightOne, SEARCH_ICON)
//        mRightTwoIcon = typedArray.getInteger(R.styleable.HeaderBar_rightTwo, TRANSFORM_LIST_ICON)

        headerView = View.inflate(context,R.layout.header_layout,this)

        initBind()
        initView()
//        typedArray.recycle()

    }

    private fun initBind() {
        mInflater = LayoutInflater.from(context)
    }

    private fun initView() {

        showHeaderImage()

        //visibleTabs()
        
        showRightOne()

        showRightTwo()

    }

    fun initClick(cb:IHeaderBar) {
        leftIcon.setOnClickListener { cb.let { cb.onClick(leftIcon) } }
        //tab的click监听
        rightOne.setOnClickListener{cb.let { cb.onClick(rightOne) }}

        rightTwo.setOnClickListener {cb.let { cb.onClick(rightTwo) }}

    }

    fun showRightTwo()= rightTwo.setBackgroundResource(R.drawable.ic_dns_black_24dp)
//    rightTwo.setBackgroundResource(R.mipmap.icon_waterfall)
    fun showRightOne()=rightOne.setBackgroundResource(R.drawable.ic_search_black_24dp)
    //fun visibleTabs(type: Boolean)= type.let { timeTabs.setTabData(titles) }

    fun showHeaderImage() = Glide.with(this)
                .load(R.mipmap.header)
                .circleCrop()
                .into(leftIcon)
}