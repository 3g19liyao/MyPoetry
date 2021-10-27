package com.example.mypoetry.mine

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.example.mypoetry.MainActivity
import com.example.mypoetry.Model.Converter
import com.example.mypoetry.Model.EventMessage
import com.example.mypoetry.Model.User
import com.example.mypoetry.R
import com.example.mypoetry.ViewModel.MineViewModel
import com.example.mypoetry.application.BaseApplication
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus

class MineFragment: Fragment() ,View.OnClickListener {

    lateinit var bottomNavigationView: BottomNavigationView
    var userData: MutableLiveData<User> = MutableLiveData()
    lateinit var mineViewModel: MineViewModel
    lateinit var goLike: TextView
    lateinit var userHead: CircleImageView
    lateinit var userName: TextView
    lateinit var userTele: TextView
    lateinit var goBack: TextView
    lateinit var change: TextView
    lateinit var personal: TextView
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mine,container,false)


        goLike = view.findViewById(R.id.goLike)
        userHead = view.findViewById(R.id.userHead)
        userName = view.findViewById(R.id.userName)
        userTele = view.findViewById(R.id.userTele)
        goBack = view.findViewById(R.id.goBack)
        change = view.findViewById(R.id.change)
        personal = view.findViewById(R.id.personal)
        mainActivity = activity as MainActivity
        bottomNavigationView = mainActivity.navView
        bottomNavigationView.isVisible = true

        goLike.setOnClickListener(this)
        change.setOnClickListener(this)
        personal.setOnClickListener(this)
        goBack.setOnClickListener(this)

        userData = mainActivity.userData
        userName.text = userData.value?.getName()
        userTele.text = userData.value?.getTele()
        val sp = context?.getSharedPreferences("data",Context.MODE_PRIVATE)
        val converter = Converter()
        if(sp?.getString(userData.value?.getTele()," ") != " "){
            Log.d("TagTag",sp?.getString(userData.value?.getTele()," ")!!)
            val bitmap: Bitmap = converter.stringToBitmap(sp.getString(userData.value?.getTele()," ")!!)
            userHead.setImageBitmap(bitmap)
        }

//        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
//        mineViewModel.Query()
//        this.activity?.let {
//            mineViewModel.userData.observe(it, Observer {
//                userData.value = it
//                userName.text = userData.value?.getName()
//                userTele.text = userData.value?.getTele()
//                val sp = context?.getSharedPreferences("data",Context.MODE_PRIVATE)
//                val converter = Converter()
//                if(sp?.getString(userData.value?.getTele()," ") != " "){
//                    Log.d("TagTag",sp?.getString(userData.value?.getTele()," ")!!)
//                    val bitmap: Bitmap = converter.stringToBitmap(sp.getString(userData.value?.getTele()," ")!!)
//                    userHead.setImageBitmap(bitmap)
//                }
//            })
//        }

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.goLike -> {
                bottomNavigationView.isVisible = false
                val action = MineFragmentDirections.actionNavigationMineToMineLikeFragment()
                findNavController().navigate(action)
            }
            R.id.change -> {
                bottomNavigationView.isVisible = false
                val action = MineFragmentDirections.actionNavigationMineToChangeFragment()
                findNavController().navigate(action)
            }
            R.id.personal -> {
                bottomNavigationView.isVisible = false
                val action = MineFragmentDirections.actionNavigationMineToPersonalFragment()
                findNavController().navigate(action)
            }
            R.id.goBack -> {
                showDialog()
            }
        }
    }

    private fun showDialog() {
        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("是否退出登录？")
        dialog.setNegativeButton(
            "否"
        ) { _, _ -> Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show() }
        dialog.setPositiveButton(
            "是"
        ) { _, _ ->
            val pref =
                BaseApplication.context.getSharedPreferences("data", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putBoolean("isLogin", false)
            editor.apply()
            EventBus.getDefault().post(EventMessage("10"))
            Toast.makeText(context, "已退出登录!", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

}