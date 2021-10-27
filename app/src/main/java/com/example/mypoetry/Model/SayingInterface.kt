package com.example.mypoetry.Model

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SayingInterface {
    @GET("/api/comment")
    fun getSaying(@Query("id") id: String,@Query("token") token: String): Observable<Saying>
}