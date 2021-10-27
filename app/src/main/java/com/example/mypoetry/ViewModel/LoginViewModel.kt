package com.example.mypoetry.ViewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.example.mypoetry.Model.EventMessage
import com.example.mypoetry.Model.User
import com.example.mypoetry.application.BaseApplication
import org.greenrobot.eventbus.EventBus


public class LoginViewModel: ViewModel() {

    fun getCode(phone: String){
        Log.d("Tag",phone)
        BmobSMS.requestSMSCode(phone,"",object : QueryListener<Int>(){
            override fun done(t: Int?, e: BmobException?) {
                if(e == null){
                    Log.d("Tag","发送成功")
                }else{
                    Log.d("Tag","发送失败")
                }
            }
        })
    }

    fun find(phone: String,password:String){
        var userBmobQuery: BmobQuery<User> = BmobQuery()
        userBmobQuery.addWhereEqualTo("tele",phone)
        userBmobQuery.findObjects(object : FindListener<User>(){
            override fun done(`object`: MutableList<User>?, e: BmobException?) {
                if(e == null){
                    Log.d("Tag","查询成功！!!")
                    if(`object`?.get(0)?.getPassword() == password){
                        val sp = BaseApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
                        val edit = sp.edit()
                        edit.putString("telephone",phone)
                        edit.apply()
                        EventBus.getDefault().post(EventMessage("3"))
                    }else{
                        EventBus.getDefault().post(EventMessage("4"))
                    }
                }else{
                    Log.d("Tag","查询失败！")
                }
            }
        })
    }

    fun checkMes(phone: String, code: String){
        BmobSMS.verifySmsCode(phone,code,object: UpdateListener(){
            override fun done(e: BmobException?) {
                if(e == null){
                    EventBus.getDefault().post(EventMessage("1"))
                    Log.d("Tag","验证成功")
                }else{
                    EventBus.getDefault().post(EventMessage("2"))
                    Log.d("Tag","验证失败")
                }
            }
        })
    }

}