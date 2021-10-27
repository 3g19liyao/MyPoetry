package com.example.mypoetry.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypoetry.Model.HotComment
import com.example.mypoetry.Model.Saying
import com.example.mypoetry.Model.SayingData
import com.example.mypoetry.Model.SayingInterface
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SayingViewModel: ViewModel() {

    var sayingData: MutableLiveData<SayingData>? = MutableLiveData()

    fun gatSaying(){
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://v2.alapi.cn/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val sayingInterface: SayingInterface = retrofit.create(SayingInterface::class.java)
        val id = "1400256289"
        val token = "wjUrnCoFLgdHfAKX"
        sayingInterface.getSaying(id,token)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribeBy(
                        onNext = {
                            sayingData?.postValue(it.data)
                            Log.d("Tag","请求数据成功")
                        },
                        onError = {
                            Log.d("Tag","请求数据失败")
                        }
                )
    }

}