package com.example.mypoetry.mine

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cn.bmob.v3.Bmob
import com.example.mypoetry.MainActivity
import com.example.mypoetry.R
import com.example.mypoetry.ViewModel.MineViewModel

class ChangeFragment: Fragment() ,View.OnClickListener{

    lateinit var mineViewModel: MineViewModel
    lateinit var historyPassword: EditText
    lateinit var newPassword: EditText
    lateinit var change: Button
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_change,container,false)

        historyPassword = view.findViewById(R.id.history_password)
        newPassword = view.findViewById(R.id.new_password)
        change = view.findViewById(R.id.change)
        mineViewModel = ViewModelProvider(this).get(MineViewModel::class.java)
        mainActivity = activity as MainActivity

        change.setOnClickListener(this)
        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.change -> {
                val old = historyPassword.text.toString()
                val new = newPassword.text.toString()

                if(old.equals(mainActivity.userData.value?.getPassword())){
                    mainActivity.userData.value?.setPassword(new)
                    mainActivity.userData.value?.getPassword()?.let { Log.d("Tag", it) }
                    mineViewModel.update(mainActivity.userData.value!!)
                }else{
                    Toast.makeText(Bmob.getApplicationContext(), "原密码输入有误！", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}