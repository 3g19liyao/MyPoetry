package com.example.mypoetry.ViewModel

import android.R.attr.password
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mypoetry.Model.Poet
import com.example.mypoetry.Model.PoetInterface
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class PoetViewModel: ViewModel() {

    var poet: MutableLiveData<Poet>? = MutableLiveData()

    fun getPoet(){
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://v2.jinrishici.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val poetInterface: PoetInterface = retrofit.create(PoetInterface::class.java)
        poetInterface.getPoet()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribeBy(
                onNext = {
                    Log.d("Tag","请求数据成功")
                    Log.d("Tag",it.data.content)
                    poet?.postValue(it)
                },
                onError = {
                    Log.d("Tag","请求数据失败")
                }
            )
    }
}