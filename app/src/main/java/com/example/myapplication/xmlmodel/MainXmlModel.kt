package com.example.myapplication.xmlmodel

import android.view.View.OnClickListener
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class MainXmlModel:ViewModel() {
    val text1 = ObservableField("")
    val text2 = ObservableField("")
    val isShow = ObservableBoolean(true)

    var btOnclick:OnClickListener? = null
}