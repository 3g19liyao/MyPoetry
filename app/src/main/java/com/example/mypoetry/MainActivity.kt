package com.example.mypoetry

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mypoetry.Model.EventMessage
import com.example.mypoetry.Model.User
import com.example.mypoetry.ViewModel.MineViewModel
import com.example.mypoetry.ViewModel.SayingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.mypoetry.ViewModel.PoetViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity(){

    public var userData: MutableLiveData<User> = MutableLiveData()
    lateinit var navView: BottomNavigationView
    lateinit var appBar: AppBarConfiguration
    lateinit var navController: NavController
    lateinit var viewModelSaying:SayingViewModel
    lateinit var mineViewModel: MineViewModel

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)//取消默认标题栏
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.TRANSPARENT);//将通知栏设置为透明色。
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)//将标题栏内容设置为黑色
        }



        viewModelSaying = ViewModelProvider(this).get(SayingViewModel::class.java)
        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)

        mineViewModel.Query()
        this.let {
            mineViewModel.userData.observe(it, Observer {
                Log.d("Tag","TAGTAGTAG")
                userData.value = it

            })
        }

        navView = findViewById(R.id.nav_view)
        navController = findNavController(R.id.nav_host_fragment)
        appBar = AppBarConfiguration(setOf(
            R.id.navigation_space,R.id.navigation_other,R.id.navigation_mine
        ))
        //setupActionBarWithNavController(navController,appBar)
        navView.setupWithNavController(navController)
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    fun showEventMessage(message: EventMessage){
        when (message.account){
            "10" -> {
                val edit = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
                edit.putBoolean("isLogin",false)
                edit.apply()
                val intent = Intent(this,LoginActivity::class.java)
                overridePendingTransition(0, 0);
                startActivity(intent)
                finish()
                overridePendingTransition(0, 0);
            }
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

}