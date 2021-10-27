package com.example.mypoetry.application

import android.app.Application
import android.content.Context
import cn.bmob.v3.Bmob

class BaseApplication: Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Bmob.initialize(this, "5c19d7524d2323a508e23cd375348a7e");
    }
}