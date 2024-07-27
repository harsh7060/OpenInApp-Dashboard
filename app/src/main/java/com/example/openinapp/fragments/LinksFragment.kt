package com.example.openinapp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.openinapp.R
import com.example.openinapp.Utils.Constant
import com.example.openinapp.adapters.AnalyticsAdapter
import com.example.openinapp.adapters.ViewPageAdapter
import com.example.openinapp.api.ApiClient
import com.example.openinapp.api.ApiService
import com.example.openinapp.databinding.FragmentLinksBinding
import com.example.openinapp.models.DashboardResponse
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class LinksFragment : Fragment() {
    private lateinit var binding: FragmentLinksBinding
    private lateinit var analyticsAdapter: AnalyticsAdapter
    private var entries = mutableListOf<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinksBinding.inflate(LayoutInflater.from(context))

        setUpTabBar()

        updateGreeting()

        setUpSupport()

        setUpRV()

        setUpGraph()

        populatingGraphData()

        return binding.root
    }

    private fun setUpSupport() {
        val apiClient = ApiClient(requireContext())
        val apiService = apiClient.retrofit.create(ApiService::class.java)

        apiService.getDashboardData().enqueue(object : Callback<DashboardResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                if (response.isSuccessful) {
                    val dashboardData = response.body()
                    if (dashboardData != null) {
                        val supportNumber = dashboardData.support_whatsapp_number.toString()
                        binding.btnWhatsapp.setOnClickListener{
                            val intent = Intent(Intent.ACTION_DIAL)
                            intent.data = Uri.parse("tel:"+"+91${supportNumber}")
                            startActivity(intent)
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateGreeting() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        val greeting = when (hour) {
            in 5..11 -> "Good Morning"
            in 12..16 -> "Good Afternoon"
            in 17..20 -> "Good Evening"
            else -> "Good Night"
        }
        binding.tvGreeting.text = greeting
    }

    private fun populatingGraphData() {
        val apiClient = ApiClient(requireContext())
        val apiService = apiClient.retrofit.create(ApiService::class.java)

        apiService.getDashboardData().enqueue(object : Callback<DashboardResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<DashboardResponse>, response: Response<DashboardResponse>) {
                if (response.isSuccessful) {
                    val dashboardData = response.body()
                    if (dashboardData != null) {
                        val hourlyData = dashboardData.data.overall_url_chart.toMap()
                        updateChart(hourlyData)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "API call failed", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateChart(hourlyData: Map<String, Int>) {
        entries = mutableListOf<Entry>()
        var index = 0
        for ((key, value) in hourlyData) {
            if(index<=23){
                entries.add(Entry(index.toFloat(), value.toFloat()))
                index++
            }else break
        }
    }

    private fun setUpGraph() {
        val chart = binding.chart
        val lineDataSet = LineDataSet(dataSet(), "Label")
        lineDataSet.color = ContextCompat.getColor(requireContext(), R.color.blue)
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_blue)

        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)

        lineDataSet.lineWidth = 2f

        val lineData = LineData(lineDataSet)
        chart.data = lineData

        // Customize the chart
        chart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        chart.setDrawGridBackground(false)
        chart.description.isEnabled = false

        val xAxis: XAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.chartTextColor))
        xAxis.setAxisLineColor(ContextCompat.getColor(requireContext(), R.color.chartAxisColor))

        val leftAxis: YAxis = chart.axisLeft
        leftAxis.setDrawGridLines(false)
        leftAxis.setTextColor(ContextCompat.getColor(requireContext(), R.color.chartTextColor))
        leftAxis.setAxisLineColor(ContextCompat.getColor(requireContext(), R.color.chartAxisColor))

        val rightAxis: YAxis = chart.axisRight
        rightAxis.isEnabled = false

        val legend: Legend = chart.legend
        legend.isEnabled = false

        chart.invalidate()
    }

    private fun dataSet(): ArrayList<com.github.mikephil.charting.data.Entry> {
        val list = ArrayList<com.github.mikephil.charting.data.Entry>()
        list.add(com.github.mikephil.charting.data.Entry(0f,0f))
        list.add(com.github.mikephil.charting.data.Entry(1f,0f))
        list.add(com.github.mikephil.charting.data.Entry(2f,00f))
        list.add(com.github.mikephil.charting.data.Entry(3f,0f))
        list.add(com.github.mikephil.charting.data.Entry(4f,0f))
        list.add(com.github.mikephil.charting.data.Entry(5f,0f))
        list.add(com.github.mikephil.charting.data.Entry(6f,0f))
        list.add(com.github.mikephil.charting.data.Entry(7f,0f))
        list.add(com.github.mikephil.charting.data.Entry(8f,0f))
        list.add(com.github.mikephil.charting.data.Entry(9f,0f))
        list.add(com.github.mikephil.charting.data.Entry(10f,0f))
        list.add(com.github.mikephil.charting.data.Entry(11f,0f))
        list.add(com.github.mikephil.charting.data.Entry(12f,2f))
        list.add(com.github.mikephil.charting.data.Entry(13f,0f))
        list.add(com.github.mikephil.charting.data.Entry(14f,0f))
        list.add(com.github.mikephil.charting.data.Entry(15f,0f))
        list.add(com.github.mikephil.charting.data.Entry(16f,0f))
        list.add(com.github.mikephil.charting.data.Entry(17f,0f))
        list.add(com.github.mikephil.charting.data.Entry(18f,0f))
        list.add(com.github.mikephil.charting.data.Entry(19f,0f))
        list.add(com.github.mikephil.charting.data.Entry(20f,0f))
        list.add(com.github.mikephil.charting.data.Entry(21f,0f))
        list.add(com.github.mikephil.charting.data.Entry(22f,0f))
        list.add(com.github.mikephil.charting.data.Entry(23f,0f))

        return list
    }

    private fun setUpRV() {
        val list = Constant.analyticsItemList
        analyticsAdapter = AnalyticsAdapter(list)
        binding.rvAnalytics.adapter = analyticsAdapter
    }

    private fun setUpTabBar() {
        val tabLayout: TabLayout = binding.tabLayout
        val viewPager: ViewPager2 = binding.viewPager

        viewPager.adapter = ViewPageAdapter(requireActivity())

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "Top Links" else "Recent Links"
        }.attach()

        for (i in 0..3){
            val textView = LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null) as TextView
            binding.tabLayout.getTabAt(i)?.customView = textView
        }
    }
}