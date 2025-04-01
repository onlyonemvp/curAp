package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R

@SuppressLint("Recycle", "CustomViewStyleable")
class Cview @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var buttonColor:Int
    init {
        context.obtainStyledAttributes(attrs, R.styleable.CustomProgressView).apply {
            buttonColor = this.getColor(
                R.styleable.CustomProgressView_buttonColor,
                ResourcesCompat.getColor(resources, R.color.black, null)
            )

            recycle()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

}