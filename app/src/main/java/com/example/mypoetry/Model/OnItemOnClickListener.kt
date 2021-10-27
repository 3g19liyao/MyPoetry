package com.example.mypoetry.Model

import android.view.View

interface OnItemOnClickListener {
    fun onItemOnClick(view: View?, pos: Int)
    fun onItemLongOnClick(view: View?, pos: Int,str: String)
}