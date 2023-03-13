package com.example.apiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    lateinit var shimmerFrameLayout: ShimmerFrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView=findViewById(R.id.recyclerView)
        shimmerFrameLayout=findViewById(R.id.shimmer)

        val retrofitBuilder= Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData= retrofitBuilder.getProductData()

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                // if api call is success,then use the data of API and show in android app
                val responseBody=response.body()
                val productList= responseBody?.products!!

                myAdapter= MyAdapter(this@MainActivity,productList)

                recyclerView.adapter=myAdapter
                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.setVisibility(View.GONE)
                recyclerView.setVisibility(View.VISIBLE)

                recyclerView.layoutManager=LinearLayoutManager(this@MainActivity)
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                // if api call is fails

                Log.d("MainActivity","OnFailure"+t.message)

            }
        })
    }
}