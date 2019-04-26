package com.example.gesang.emotion.view.selfview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.gesang.emotion.R

internal class ListHolder(val view: View?) {
    var mLeftIcon: ImageView =view!!.findViewById(R.id.menuIcon)
    var mMenuTitle: TextView =view!!.findViewById(R.id.menuTitle)
    var selected = false

}