package com.example.mypoetry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.mypoetry.ViewModel.LoginViewModel

class LoginFragment: Fragment(),View.OnClickListener {

    lateinit var login: Button
    lateinit var telePhone: EditText
    lateinit var password: EditText
    lateinit var regist: Button
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login,container,false)

        regist = view.findViewById(R.id.regist)
        telePhone = view.findViewById(R.id.usertelephone)
        password = view.findViewById(R.id.password)
        login = view.findViewById(R.id.login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        login.setOnClickListener(this)
        regist.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.login -> {
                val tele = telePhone.text.toString()
                val word = password.text.toString()
                loginViewModel.find(tele,word)
            }
            R.id.regist -> {
                val action1: NavDirections = LoginFragmentDirections.actionLoginFragmentToRegistFragment()
                Navigation.findNavController(v).navigate(action1)
            }
        }
    }
}