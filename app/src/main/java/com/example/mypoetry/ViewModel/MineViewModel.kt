package com.example.mypoetry.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.UpdateListener
import com.example.mypoetry.Model.User
import com.example.mypoetry.application.BaseApplication

class MineViewModel: ViewModel() {

    val userData: MutableLiveData<User> = MutableLiveData()

    fun Query(){
        var userBmobQuery: BmobQuery<User> = BmobQuery()
        val telePhone = BaseApplication.context.getSharedPreferences("data",Context.MODE_PRIVATE).getString("telephone","null")
        userBmobQuery.addWhereEqualTo("tele",telePhone)
        userBmobQuery.findObjects(object : FindListener<User>(){
            override fun done(`object`: MutableList<User>?, e: BmobException?) {
                if(e == null){
                    userData.postValue(`object`!![0])
                    Log.d("Tag","查询成功！!!")
                }else{
                    Log.d("Tag","查询失败！")
                }
            }
        })
//        val bmobQuery: BmobQuery<User> = BmobQuery()
//        bmobQuery.getObject(objectId,object: QueryListener<User>(){
//            override fun done(t: User?, e: BmobException?) {
//                if(e == null){
//                    Log.d("Tag","查询成功！")
//                    if (t != null) {
//                        userData.postValue(t)
//                    }
//                }else{
//                    Log.d("Tag","查询失败！")
//                }
//            }
//        })
    }

    fun update(user: User){
        user.update(object: UpdateListener(){
            override fun done(e: BmobException?) {
                if(e == null){
                    Query()
                    Log.d("Tag","数据更新成功！")
                }else{
                    Log.d("Tag","$e...数据更新失败！")
                }
            }
        })
    }
}