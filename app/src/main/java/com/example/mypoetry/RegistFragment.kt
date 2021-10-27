package com.example.mypoetry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.mypoetry.Model.EventMessage
import com.example.mypoetry.Model.VerificationCodeTextView
import com.example.mypoetry.ViewModel.LoginViewModel
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class RegistFragment: Fragment(),View.OnClickListener {

    lateinit var register: Button
    lateinit var telePhone: EditText
    lateinit var code: EditText
    lateinit var loginViewModel: LoginViewModel
    private var verificationCodeTextView: VerificationCodeTextView?= null
    var v: View? = null

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_regist,container,false)


        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        register = v?.findViewById(R.id.register)!!
        telePhone = v?.findViewById(R.id.telePhone)!!
        code = v?.findViewById(R.id.code)!!
        verificationCodeTextView = v?.findViewById(R.id.getMes)

        verificationCodeTextView?.setOnClickListener(this)
        code.setOnClickListener(this)
        register.setOnClickListener(this)


        return v
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.register -> {
                loginViewModel.checkMes(telePhone.text.toString(),code.text.toString())
            }
            R.id.getMes -> {
                Log.d("Tag","点击获取验证码")
                loginViewModel.getCode(telePhone.text.toString())
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    fun showEventMessage(message: EventMessage){
        when (message.account){
            "1" -> {
                val action1: NavDirections = RegistFragmentDirections.actionRegistFragmentToSetFragment(telePhone.text.toString())
                v?.let { Navigation.findNavController(it).navigate(action1) }
                Log.d("Tag","注册成功！")
            }
            "2" -> {
                Log.d("Tag","验证码输入有误，注册失败")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }
}