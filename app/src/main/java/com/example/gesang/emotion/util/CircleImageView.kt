package com.example.gesang.emotion.util

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.gesang.emotion.R

class CircleImageView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private  var mPaint: Paint
    private  var mTargetPaint: Paint
    private  var mSourceBitmap: Bitmap
    private  var mTargetBitmap: Bitmap
    private  var mTargetCanvas: Canvas

    private var mWidth= 0
    private var mHeight = 0

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTargetPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTargetPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

        mSourceBitmap = BitmapFactory.decodeResource(resources, R.mipmap.header)
        mTargetBitmap = Bitmap.createBitmap(mSourceBitmap.width, mSourceBitmap.height,
                Bitmap.Config.ARGB_8888)
        mTargetCanvas = Canvas(mTargetBitmap)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        //生成圆形bitmap过程
        var radius = Math.min(mWidth,mHeight)/2
        //绘制圆形
        mTargetCanvas.drawCircle((mWidth/2).toFloat(), (mHeight/2).toFloat(), radius.toFloat(),mPaint)
        //再绘制Bitmap
        mTargetCanvas.drawBitmap(mSourceBitmap,0f,0f,mTargetPaint)

        canvas!!.drawBitmap(mTargetBitmap,0f,0f,null)
    }
}