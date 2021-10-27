package com.example.mypoetry.Model

class EventMessage(var account: String) {
    fun putMessage(value: String){
        account = value
    }
    fun getMessage(): String {
        return account
    }
}

