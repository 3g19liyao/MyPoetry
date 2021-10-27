package com.example.mypoetry.mine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.Bmob.getApplicationContext
import com.example.mypoetry.MainActivity
import com.example.mypoetry.Model.OnItemOnClickListener
import com.example.mypoetry.Model.User
import com.example.mypoetry.R
import com.example.mypoetry.ViewModel.MineViewModel
import com.example.mypoetry.adapter.PoetLikeAdapter
import com.example.mypoetry.adapter.SayingLikeAdapter
import com.example.mypoetry.application.BaseApplication

class PoetLikeFragment:Fragment() {

    var userData: MutableLiveData<User> = MutableLiveData()
    lateinit var mineViewModel: MineViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: PoetLikeAdapter
    lateinit var s: ArrayList<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_poetlike,container,false)

        recyclerView = view.findViewById(R.id.poetRecycle)
        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)

        val activity = activity as MainActivity
        userData = activity.userData
        s = userData.value?.getPoetList()!!

        val layoutManager = LinearLayoutManager(BaseApplication.context)
        recyclerView.layoutManager = layoutManager
        adapter = PoetLikeAdapter(s)
        adapter.setOnItemClickListener(object : OnItemOnClickListener{
            override fun onItemOnClick(view: View?, pos: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemLongOnClick(view: View?, pos: Int,str: String) {
                showPopMenu(view!!,pos,str)
            }

        })
        recyclerView.adapter = adapter

        return view
    }

    fun showPopMenu(view: View,pos: Int,str:String){
        val popupMenu = PopupMenu(BaseApplication.context,view)
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                adapter.removeItem(pos)
                userData.value?.getPoetList()?.remove(str)
                mineViewModel.update(userData.value!!)
                return false
            }
        })
        popupMenu.setOnDismissListener(object :PopupMenu.OnDismissListener{
            override fun onDismiss(menu: PopupMenu?) {
                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show()
            }
        })
        popupMenu.show()
    }


}