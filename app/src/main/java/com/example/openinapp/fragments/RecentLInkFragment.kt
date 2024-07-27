package com.example.openinapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openinapp.adapters.RecentLinkAdapter
import com.example.openinapp.api.ApiClient
import com.example.openinapp.api.ApiService
import com.example.openinapp.databinding.FragmentRecentLInkBinding
import com.example.openinapp.models.DashboardResponse
import com.example.openinapp.models.RecentLink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecentLInkFragment : Fragment() {
    private lateinit var binding: FragmentRecentLInkBinding
    private val recentLinks: MutableList<RecentLink> = mutableListOf()
    private lateinit var adapter: RecentLinkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecentLInkBinding.inflate(inflater, container, false)

        adapter = RecentLinkAdapter(recentLinks)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        val apiClient = ApiClient(requireContext())
        val apiService = apiClient.retrofit.create(ApiService::class.java)

        apiService.getDashboardData().enqueue(object : Callback<DashboardResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                if (response.isSuccessful) {
                    val dashboardData = response.body()
                    if (dashboardData != null) {
                        recentLinks.clear()
                        recentLinks.addAll(dashboardData.data.recent_links)
                        adapter.notifyDataSetChanged()
                    } else {
                        Log.d("API Error", "No data received")
                    }
                } else {
                    Log.d("API Error", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Log.d("API Error", "API call failed", t)
            }
        })

        return binding.root
    }
}
