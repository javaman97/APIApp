package com.example.apiapp

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    fun getProductData(): Call<MyData>
}