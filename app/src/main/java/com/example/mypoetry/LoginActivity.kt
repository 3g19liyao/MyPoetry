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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mypoetry.Model.EventMessage
import com.example.mypoetry.ViewModel.MineViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LoginActivity : AppCompatActivity() , View.OnClickListener{



    lateinit var mineViewModel: MineViewModel
    var fragment: NavHostFragment? = null
    var controller: NavController? = null

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)//取消默认标题栏
        setContentView(R.layout.activity_login)

        if(Build.VERSION.SDK_INT>=21){
            getWindow().setStatusBarColor(Color.TRANSPARENT);//将通知栏设置为透明色。
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)//将标题栏内容设置为黑色
        }

        fragment = supportFragmentManager.findFragmentById(R.id.nav_host_Loginfragment) as NavHostFragment?
        controller = fragment!!.navController

        val sp = getSharedPreferences("data", Context.MODE_PRIVATE)
        val isLogin = sp.getBoolean("isLogin",false)
        if(isLogin){
            val intent = Intent(this,MainActivity::class.java)
            overridePendingTransition(0, 0);
            mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
            mineViewModel.Query()
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    fun showEventMessage(message: EventMessage){
        when (message.account){
            "3" -> {
                Log.d("Tag","登陆成功")
                val sp = getSharedPreferences("data", Context.MODE_PRIVATE)
                val edit = sp.edit()
                edit.putBoolean("isLogin",true)
                edit.apply()
                val intent = Intent(this,MainActivity::class.java)
                overridePendingTransition(0, 0);
                startActivity(intent)
                finish()
                overridePendingTransition(0, 0);
            }
            "4" -> {
                Log.d("Tag","登陆失败")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}