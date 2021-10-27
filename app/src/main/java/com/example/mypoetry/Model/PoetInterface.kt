package com.example.mypoetry.Model

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface PoetInterface {
    @GET("/one.json")
    fun getPoet(): Observable<Poet>
}
