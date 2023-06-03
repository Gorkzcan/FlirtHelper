package com.gorkem.flirthelper.service

import com.gorkem.flirthelper.model.Question
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("Gorkzcan/FlirtHelper/main/flortsorulari.json")
    fun getQuestion(): Observable<List<Question>>
}