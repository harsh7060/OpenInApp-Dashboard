package com.example.openinapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openinapp.Utils.Constant
import com.example.openinapp.adapters.TopLinksAdapter
import com.example.openinapp.api.ApiClient
import com.example.openinapp.api.ApiService
import com.example.openinapp.databinding.FragmentTopLinkBinding
import com.example.openinapp.models.DashboardResponse
import com.example.openinapp.models.TopLink
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopLinkFragment : Fragment() {
    private lateinit var binding: FragmentTopLinkBinding
    private val topLinks: MutableList<TopLink> = mutableListOf()
    private lateinit var adapter: TopLinksAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopLinkBinding.inflate(inflater, container, false)

        adapter = TopLinksAdapter(topLinks)
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
                        topLinks.clear()
                        topLinks.addAll(dashboardData.data.top_links)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "API call failed", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}
