package com.example.myapplication.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.example.myapplication.databinding.ActivityMainBinding


abstract class BaseActivity<VB:ViewDataBinding>: AppCompatActivity() {
    protected lateinit var binding: VB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,getLayoutId())
        binding.lifecycleOwner = this // 关键：绑定生命周期
        initData()
        initViewModel()
        initView()
    }


    abstract fun getLayoutId(): Int

    abstract fun initData()

    abstract fun initViewModel()

    protected fun initView() {
    }



    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return super.onCreateView(parent, name, context, attrs)
    }




    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }


    override fun onDestroy() {
        super.onDestroy()
    }


}