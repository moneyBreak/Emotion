package com.example.gesang.emotion.view.selfview

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class WaterfullDecoration(context: Context,interval:Int): RecyclerView.ItemDecoration() {

    private val context = context
    private val interval = interval

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val param = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        val spanIndex = param.spanIndex //获取spanIndex的下标，也就是措落渲染的下一个item
        var inter = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                this.interval.toFloat(),context.resources.displayMetrics).toInt()
        //中间间隔
        outRect.left = if(spanIndex % 2 == 0){
            0
        }else {
            //为奇数，就是右边二列，设置左间隔4dp
            inter
        }
        outRect.bottom = inter
    }
}