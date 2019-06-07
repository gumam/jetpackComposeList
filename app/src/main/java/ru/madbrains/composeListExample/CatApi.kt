package ru.madbrains.composeListExample

import retrofit2.Call
import retrofit2.http.GET

interface CatApi {

    @GET("/bins/w4bj6")
    fun getCats(): Call<List<Cat>>
}