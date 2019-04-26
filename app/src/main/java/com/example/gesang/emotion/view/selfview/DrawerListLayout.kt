package com.example.gesang.emotion.view.selfview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.LinearLayout

class DrawerListLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),Checkable {
    private var mChecked = false

    companion object {
        val CHECKED_STATE_SET = IntArray(android.R.attr.state_checked)
    }
    override fun isChecked()=mChecked

    override fun setChecked(checked: Boolean) {
        if (checked != mChecked) {
            mChecked = checked
            refreshDrawableState()
        }
    }
    override fun toggle() {
        setChecked(!mChecked)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace+1)
        if(isChecked) View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        return drawableState
    }
}