package com.example.myapplication.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import com.example.myapplication.R

class CustomDialog private constructor(builder: Builder) {

    private var dialog: AlertDialog? = null

    init {
        // 使用带 Theme 的 Builder 避免样式问题
        val alertBuilder = AlertDialog.Builder(builder.context, R.style.CustomDialogStyle)
            .apply {
                // 标题配置
                builder.title?.let { setTitle(it) }

                // 内容配置
                builder.message?.let { setMessage(it) }

                // 自定义布局
                builder.customView?.let { setView(it) }

                // 确认按钮
                setPositiveButton(builder.positiveText, builder.positiveAction)

                // 取消按钮
                setNegativeButton(builder.negativeText, builder.negativeAction)

                // 中立按钮
                setNeutralButton(builder.neutralText, builder.neutralAction)

                // 可取消配置
                setCancelable(builder.cancelable)
            }

        dialog = alertBuilder.create().apply {
            // 窗口属性配置
            window?.let {
                it.setGravity(builder.gravity)
                if (builder.width > 0 && builder.height > 0) {
                    it.setLayout(builder.width, builder.height)
                }
            }
        }
    }

    fun show() {
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    class Builder(val context: Context) {
        internal var title: CharSequence? = null
        internal var message: CharSequence? = null
        internal var customView: View? = null
        internal var positiveText: CharSequence = "确定"
        internal var positiveAction: DialogInterface.OnClickListener? = null
        internal var negativeText: CharSequence = "取消"
        internal var negativeAction: DialogInterface.OnClickListener? = null
        internal var neutralText: CharSequence? = null
        internal var neutralAction: DialogInterface.OnClickListener? = null
        internal var cancelable: Boolean = true
        internal var gravity: Int = Gravity.CENTER
        internal var width: Int = 0
        internal var height: Int = 0

        fun setTitle(title: CharSequence): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: CharSequence): Builder {
            this.message = message
            return this
        }

        fun setCustomView(view: View): Builder {
            this.customView = view
            return this
        }

        fun setPositiveButton(
            text: CharSequence = "确定",
            listener: DialogInterface.OnClickListener? = null
        ): Builder {
            this.positiveText = text
            this.positiveAction = listener ?: DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            }
            return this
        }

        fun setNegativeButton(
            text: CharSequence = "取消",
            listener: DialogInterface.OnClickListener? = null
        ): Builder {
            this.negativeText = text
            this.negativeAction = listener ?: DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            }
            return this
        }

        fun setNeutralButton(
            text: CharSequence,
            listener: DialogInterface.OnClickListener
        ): Builder {
            this.neutralText = text
            this.neutralAction = listener
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        fun setGravity(gravity: Int): Builder {
            this.gravity = gravity
            return this
        }

        fun setWidthAndHeight(width: Int, height: Int): Builder {
            this.width = width
            this.height = height
            return this
        }

        fun build(): CustomDialog {
            return CustomDialog(this)
        }
    }
}

// 使用示例
//CustomDialog.Builder(context)
//.setTitle("温馨提示")
//.setMessage("确认要删除这条数据吗？")
//.setPositiveButton("删除") { dialog, _ ->
//    // 执行删除操作
//    dialog.dismiss()
//}
//.setNegativeButton("取消")
//.setGravity(Gravity.BOTTOM)
//.setWidthAndHeight(800, 600)
//.build()
//.show()