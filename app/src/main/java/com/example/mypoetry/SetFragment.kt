package com.example.mypoetry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.mypoetry.Model.User
import com.example.mypoetry.application.BaseApplication

class SetFragment: Fragment(),View.OnClickListener {

    lateinit var setName: EditText
    lateinit var setPass: EditText
    lateinit var setOk: Button
    lateinit var v: View
    val args: SetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_set,container,false)

        setName = v.findViewById(R.id.setName)
        setPass = v.findViewById(R.id.setPass)
        setOk = v.findViewById(R.id.setOk)
        setOk.setOnClickListener(this)

        return v
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.setOk -> {
                val name = setName.text.toString()
                val pass = setPass.text.toString()
                if(pass != null){
                    var user = User()
                    user.setName(name)
                    user.setPassword(pass)
                    user.setTele(args.telephone)
                    user.save(object : SaveListener<String>(){
                        override fun done(t: String?, e: BmobException?) {
                            if(e == null){
                                val action = SetFragmentDirections.actionSetFragmentToLoginFragment()
                                Navigation.findNavController(v).navigate(action)
                                Log.d("Tag","设置成功！")
                            }else{
                                Log.d("Tag","设置失败！")
                            }
                        }
                    })
                }else{
                    Toast.makeText(BaseApplication.context,"密码不能为空！",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}