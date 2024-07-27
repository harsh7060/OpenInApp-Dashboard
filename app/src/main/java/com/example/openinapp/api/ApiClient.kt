package com.example.openinapp.api

import android.content.Context
import com.example.openinapp.Utils.TokenManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient(context: Context) {
    private val tokenManager = TokenManager(context)

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder: Request.Builder = original.newBuilder()
            .header("Authorization", "Bearer ${tokenManager.getToken()}")
        val request: Request = requestBuilder.build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://api.inopenapp.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
