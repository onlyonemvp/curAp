package com.example.myapplication.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Gravity
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.dialog.CustomDialog
import com.example.myapplication.xmlmodel.MainXmlModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var customDialog: CustomDialog? = null
    private val viewModel by lazy { MainXmlModel() }
    override fun initData() {

    }

    override fun initViewModel() {
        binding.bt1.setOnClickListener {
            CustomDialog.Builder(this)
                .setTitle("温馨提示")
                .setMessage("确认要删除这条数据吗？")
                .setPositiveButton("删除") { dialog, _ ->
                    // 执行删除操作
                    dialog.dismiss()
                }
                .setNegativeButton("取消")
                .setGravity(Gravity.BOTTOM)
                .setWidthAndHeight(300, 300)
                .build()
                .show()
            viewModel.text1.set("点了")
        }
        viewModel.text1.set("ddddddddd")
        viewModel.btOnclick = View.OnClickListener {
            viewModel.text2.set("rc")
        }
        binding.viewModel = viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }




    val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {

                }

                2 -> {

                }

                3 -> {

                }
            }
        }

    }




}