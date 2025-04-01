package com.example.myapplication.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.Gravity
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.dialog.CustomDialog
import com.example.myapplication.view.CustomProgressView
import com.example.myapplication.xmlmodel.MainXmlModel
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlin.math.abs

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var customDialog: CustomDialog? = null
    private val viewModel by lazy { MainXmlModel() }
    override fun initData() {

    }

    override fun initViewModel() {
        binding.bt1.setOnClickListener {
            aaa()
            LinkNum(4)
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
            XXPermissions.with(this).permission(
                Permission.CAMERA,
                Permission.ACCESS_FINE_LOCATION
            ).request { permissions, allGranted ->
                if (!allGranted) {
                    //toast("获取部分权限成功，但部分权限未正常授予")
                    //return
                }
                //toast("获取录音和日历权限成功")
            }
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

    fun main(num :Int) {
        val numStr = "1234"  // 输入的字符串
        var list = ArrayList<String>()
        // 外层循环遍历每个起始位置
        for (start in 0 until numStr.length) {
            // 内层循环遍历每个结束位置
            for (end in start + 1..numStr.length) {
                // 截取子串
                val substring = numStr.substring(start, end)
                list.add(substring)

                list.filter {
                    (it.toInt() ?: 0) < num
                }

            }
        }
    }

    val n = 4
    val inx =  3

    val numList = ArrayList<Int>()
    fun aaa() {
        numList.clear()
        for ( i in 1..n){
            numList.add(i)
        }

        Log.v("选择 $ 个数字的结果：","一共为${numList.size} , 选择个数：$inx =>")

        val a = numList.per(inx)
        var strSub = ""
        a.forEach{ list ->
            list.forEach {
                strSub += it.toString()
            }
            strSub +"\n"
        }
        Log.v("可选结果","$strSub")
    }

    fun <T> List<T>.per(k: Int): List<List<T>> {
        if (k == 0) return listOf(emptyList())
        if (this.isEmpty()) return emptyList()

        val head = this[0]
        val tail = this.drop(1)

        val withHead = tail.per(k - 1).map { listOf(head) + it }
        val withoutHead = tail.per(k)

        return withHead + withoutHead
    }


    fun LinkNum(n :Int) {
        val visited = BooleanArray(n + 1)  // 记录使用过的数字
        val path = mutableListOf<Int>()    // 当前排列
        val result = mutableListOf<List<Int>>()  // 结果集合

        fun backtrack() {
            if (path.size == n) {
                result.add(path.toList())  // 找到一个满足条件的排列
                return
            }

            for (i in 1..n) {
                if (!visited[i] && (path.isEmpty() || Math.abs(path.last() - i) != 1)) {
                    // 选择 i
                    visited[i] = true
                    path.add(i)

                    backtrack()  // 递归下一层

                    // 回溯：撤销选择
                    path.removeAt(path.size - 1)
                    visited[i] = false
                }
            }
        }

        backtrack()

        // 输出所有结果
        for (perm in result) {
            println(perm.joinToString(" "))
        }
    }







}