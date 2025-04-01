package com.example.myapplication.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.myapplication.R

class CustomProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 画笔对象
    private val buttonPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressFgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 尺寸参数
    private var buttonRadius = 0f
    private var progressHeight = 0f
    private var textSize = 0f

    // 颜色参数
    private var buttonColor = Color.BLUE
    private var progressBgColor = Color.GRAY
    private var progressFgColor = Color.GREEN
    private var textColor = Color.BLACK

    // 逻辑参数
    private var maxProgress = 100
    private var currentProgress = 50
    private var isDragging = false

    // 坐标参数
    private var plusButtonCenter = PointF()
    private var minusButtonCenter = PointF()
    private var progressRect = RectF()

    // 回调接口
    var onProgressChanged: ((Int) -> Unit)? = null

    init {
        // 初始化参数
        buttonRadius = dp2px(15f)
        progressHeight = dp2px(8f)
        textSize = dp2px(14f)

        // 解析自定义属性
        context.obtainStyledAttributes(attrs, R.styleable.CustomProgressView).apply {
            buttonColor = getColor(
                R.styleable.CustomProgressView_buttonColor,
                ResourcesCompat.getColor(resources, R.color.black, null)
            )
            progressBgColor = getColor(
                R.styleable.CustomProgressView_progressBgColor,
                Color.GRAY
            )
            progressFgColor = getColor(
                R.styleable.CustomProgressView_progressFgColor,
                Color.GREEN
            )
            textColor = getColor(
                R.styleable.CustomProgressView_textColor,
                Color.BLACK
            )
            maxProgress = getInt(R.styleable.CustomProgressView_maxProgress, 100)
            currentProgress = getInt(R.styleable.CustomProgressView_progress, 50)
            recycle()
        }

        // 初始化画笔
        with(buttonPaint) {
            color = buttonColor
            style = Paint.Style.FILL
        }

        with(progressBgPaint) {
            color = progressBgColor
            style = Paint.Style.FILL
        }

        with(progressFgPaint) {
            color = progressFgColor
            style = Paint.Style.FILL
        }

        with(textPaint) {
            color = textColor
            textSize = this@CustomProgressView.textSize
            textAlign = Paint.Align.CENTER
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = (buttonRadius * 4 * 2 + progressHeight * 4).toInt()
        val minHeight = (buttonRadius * 2 * 2).toInt()

        val width = resolveSize(minWidth, widthMeasureSpec)
        val height = resolveSize(minHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updatePositions()
    }

    private fun updatePositions() {
        // 按钮位置
        minusButtonCenter.set(buttonRadius + paddingStart, height / 2f)
        plusButtonCenter.set(width - buttonRadius - paddingEnd, height / 2f)

        // 进度条区域
        val progressTop = height / 2f - progressHeight / 2
        val progressBottom = height / 2f + progressHeight / 2
        progressRect.set(
            minusButtonCenter.x + buttonRadius + dp2px(8f),
            progressTop,
            plusButtonCenter.x - buttonRadius - dp2px(8f),
            progressBottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        drawButtons(canvas)
        drawProgressBar(canvas)
        drawProgressText(canvas)
    }

    private fun drawButtons(canvas: Canvas) {
        // 绘制减号按钮
        canvas.drawCircle(minusButtonCenter.x, minusButtonCenter.y, buttonRadius, buttonPaint)
        canvas.drawLine(
            minusButtonCenter.x - buttonRadius / 2,
            minusButtonCenter.y,
            minusButtonCenter.x + buttonRadius / 2,
            minusButtonCenter.y,
            progressFgPaint.apply { strokeWidth = dp2px(2f) }
        )

        // 绘制加号按钮
        canvas.drawCircle(plusButtonCenter.x, plusButtonCenter.y, buttonRadius, buttonPaint)
        canvas.drawLine(
            plusButtonCenter.x - buttonRadius / 2,
            plusButtonCenter.y,
            plusButtonCenter.x + buttonRadius / 2,
            plusButtonCenter.y,
            progressFgPaint
        )
        canvas.drawLine(
            plusButtonCenter.x,
            plusButtonCenter.y - buttonRadius / 2,
            plusButtonCenter.x,
            plusButtonCenter.y + buttonRadius / 2,
            progressFgPaint
        )
    }

    private fun drawProgressBar(canvas: Canvas) {
        // 背景
        canvas.drawRoundRect(progressRect, progressHeight / 2, progressHeight / 2, progressBgPaint)

        // 前景
        val progressWidth = progressRect.width() * currentProgress / maxProgress
        val fgRect = RectF(
            progressRect.left,
            progressRect.top,
            progressRect.left + progressWidth,
            progressRect.bottom
        )
        canvas.drawRoundRect(fgRect, progressHeight / 2, progressHeight / 2, progressFgPaint)
    }

    private fun drawProgressText(canvas: Canvas) {
        val textY = progressRect.centerY() - (textPaint.descent() + textPaint.ascent()) / 2
        canvas.drawText("$currentProgress%", progressRect.centerX(), textY, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchDown(event.x, event.y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging) {
                    updateProgressByDrag(event.x)
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                isDragging = false
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun handleTouchDown(x: Float, y: Float) {
        when {
            isInCircle(x, y, minusButtonCenter, buttonRadius) -> {
                currentProgress = (currentProgress - 5).coerceAtLeast(0)
                onProgressChanged?.invoke(currentProgress)
                invalidate()
            }
            isInCircle(x, y, plusButtonCenter, buttonRadius) -> {
                currentProgress = (currentProgress + 5).coerceAtMost(maxProgress)
                onProgressChanged?.invoke(currentProgress)
                invalidate()
            }
            progressRect.contains(x, y) -> {
                isDragging = true
                updateProgressByDrag(x)
            }
        }
    }

    private fun updateProgressByDrag(x: Float) {
        val newProgress = ((x - progressRect.left) / progressRect.width() * maxProgress)
            .coerceIn(0f, maxProgress.toFloat())
            .toInt()
        if (newProgress != currentProgress) {
            currentProgress = newProgress
            onProgressChanged?.invoke(currentProgress)
            invalidate()
        }
    }

    private fun isInCircle(x: Float, y: Float, center: PointF, radius: Float): Boolean {
        val dx = x - center.x
        val dy = y - center.y
        return dx * dx + dy * dy <= radius * radius
    }

    private fun dp2px(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }

    // 公开方法
    fun setProgress(progress: Int) {
        currentProgress = progress.coerceIn(0, maxProgress)
        invalidate()
    }

    fun getProgress(): Int = currentProgress
}