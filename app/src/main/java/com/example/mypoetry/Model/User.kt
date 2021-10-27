package com.example.mypoetry.Model

import cn.bmob.v3.BmobObject
import cn.bmob.v3.datatype.BmobFile
import java.io.File


class User: BmobObject() {

    private var name: String? = null
    private var tele: String? = null
    private var head: String? = null
    private var password: String? = null
    private var poetList: ArrayList<String> = ArrayList()
    private var sayingList: ArrayList<String> = ArrayList()
    private var gender: String? = null
    private var place: String? = null
    private var introduce: String? = null
    private var headImage: BmobFile ?= null

    fun getheadImage(): BmobFile? {
        return headImage
    }

    fun setheadImage(headImage: BmobFile?) {
        this.headImage = headImage
    }

    fun getintroduce(): String? {
        return introduce
    }

    fun setintroduce(introduce: String?) {
        this.introduce = introduce
    }

    fun getplace(): String? {
        return place
    }

    fun setplace(place: String?) {
        this.place = place
    }

    fun getgender(): String? {
        return gender
    }

    fun setgender(gender: String?) {
        this.gender = gender
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getTele(): String? {
        return tele
    }

    fun setTele(address: String?) {
        this.tele = address
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    fun getHead(): String? {
        return head
    }

    fun setHead(head: String?) {
        this.head = head
    }

    fun setPoetList(list: ArrayList<String>){
        this.poetList = list
    }

    fun getPoetList(): ArrayList<String>?{
        return poetList
    }

    fun setSayingList(list: ArrayList<String>){
        this.sayingList = list
    }

    fun getSayingPoetList(): ArrayList<String>?{
        return sayingList
    }
}





