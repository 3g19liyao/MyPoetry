package com.example.mypoetry.space

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mypoetry.MainActivity
import com.example.mypoetry.Model.Origin
import com.example.mypoetry.Model.User
import com.example.mypoetry.R
import com.example.mypoetry.ViewModel.MineViewModel
import com.example.mypoetry.ViewModel.PoetViewModel
import com.example.mypoetry.application.BaseApplication
import de.hdodenhof.circleimageview.CircleImageView

class SpaceFragment : Fragment(),View.OnClickListener {

    lateinit var poetViewModel: PoetViewModel
    lateinit var getPoet: Button
    lateinit var poet:TextView
    lateinit var poetName:TextView
    lateinit var poetPeople: TextView
    lateinit var like: CircleImageView

    var origin: Origin? = null


    var userData: MutableLiveData<User> = MutableLiveData()
    lateinit var mineViewModel: MineViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_space,container,false)

        //mainActivity = activity as MainActivity
        getPoet = view.findViewById(R.id.getPoet)
        poet = view.findViewById(R.id.poet)
        poetName = view.findViewById(R.id.poetName)
        poetPeople = view.findViewById(R.id.poetPeople)
        like = view.findViewById(R.id.like)
        like.setOnClickListener(this)
        getPoet.setOnClickListener(this)


        poetViewModel = ViewModelProvider(this).get(PoetViewModel::class.java)
        poetViewModel.getPoet()

        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
        mineViewModel.Query()
        this.activity?.let {
            mineViewModel.userData?.observe(it, Observer {
                Log.d("TagTag","传送成功")
                userData.value = it
            })
        }
        return view
    }

    override fun onResume() {
        super.onResume()

        this.activity?.let {
            poetViewModel.poet?.observe(it, Observer { it ->
                Log.d("Tagggg",it.data.content)
                poet.text = it.data.content
                poetName.text = "《${it.data.origin.title}》"
                poetPeople.text = "选自: ${it.data.origin.dynasty}  ${it.data.origin.author}"

                if(userData.value?.getPoetList() == null){
                    Log.d("TAG","listnull")
                }
                if(userData.value?.getTele() == null){
                    Log.d("TAG","telenull")
                }

                if(userData.value?.getPoetList() != null && userData.value?.getPoetList()!!.contains(poet.text.toString())){
                    like.setImageResource(R.drawable.like)
                }else{
                    like.setImageResource(R.drawable.nolike)
                }
            })
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.getPoet -> {
                poetViewModel.getPoet()
            }
            R.id.like -> {
                Log.d("Tag","点击")
                Log.d("TTTTTTTT",userData.value?.getTele().toString())
                if(userData.value?.getPoetList() != null && userData.value?.getPoetList()!!.contains(poet.text.toString())){
                    like.setImageDrawable(resources.getDrawable(R.drawable.nolike))
                    userData.value!!.getPoetList()?.remove(poet.text.toString())
                    mineViewModel.update(userData.value!!)
                    (activity as MainActivity).userData = userData
                }else{
                    like.setImageDrawable(resources.getDrawable(R.drawable.like))
                    val list = userData.value?.getPoetList()
                    list?.add(poet.text.toString())
                    mineViewModel.update(userData.value!!)
                    (activity as MainActivity).userData = userData
                    Log.d("Tag","去更新")
                }
            }
        }
    }

}
