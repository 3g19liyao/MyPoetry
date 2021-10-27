package com.example.mypoetry.other

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypoetry.MainActivity
import com.example.mypoetry.Model.HotComment
import com.example.mypoetry.Model.User
import com.example.mypoetry.R
import com.example.mypoetry.ViewModel.MineViewModel
import com.example.mypoetry.ViewModel.SayingViewModel
import com.example.mypoetry.adapter.SayingAdapter
import com.example.mypoetry.application.BaseApplication
import kotlin.math.min

class OtherFragment : Fragment() ,View.OnClickListener{

    lateinit var sayingViewModel: SayingViewModel
    var hotComment: List<HotComment>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var mineViewModel: MineViewModel
    var userData: MutableLiveData<User> = MutableLiveData()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_other,container,false)

        recyclerView = view.findViewById(R.id.sayingRecycler)

        mineViewModel = (activity as MainActivity).mineViewModel
        mineViewModel.Query()
        this.activity?.let {
            mineViewModel.userData.observe(it, Observer {
                userData.value = it
            })
        }


        sayingViewModel = (activity as MainActivity).viewModelSaying
        hotComment = sayingViewModel.sayingData?.value?.hot_comment
        if (hotComment == null){
            sendSayingRequest()
        }else{
            setRecycle()
        }
        this.activity?.let {
            sayingViewModel.sayingData?.observe(it, Observer {
                hotComment = it.hot_comment
                Log.d("Taggg", hotComment!![0].content)
                setRecycle()
            })
        }

        return view
    }

    private fun setRecycle() {
        val layoutManager = LinearLayoutManager(BaseApplication.context)
        recyclerView.layoutManager = layoutManager
        val adapter = SayingAdapter(hotComment!!,mineViewModel,userData)
        recyclerView.adapter = adapter
    }

    private fun sendSayingRequest() {
        sayingViewModel.gatSaying()
    }

    override fun onClick(v: View?) {
        when(v?.id){
        }
    }
}