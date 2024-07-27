package com.example.openinapp.api

import com.example.openinapp.models.DashboardResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/dashboardNew")
    fun getDashboardData(): Call<DashboardResponse>
}
