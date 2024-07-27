package com.example.openinapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openinapp.databinding.ItemViewAnalyticsBinding
import com.example.openinapp.models.Analytics

class AnalyticsAdapter(val list: List<Analytics>): RecyclerView.Adapter<AnalyticsAdapter.AnalyticsViewHolder>() {
    class AnalyticsViewHolder(val binding: ItemViewAnalyticsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyticsViewHolder {
        return AnalyticsViewHolder(ItemViewAnalyticsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnalyticsViewHolder, position: Int) {
        holder.binding.apply {
            tvValue.text = list[position].value
            tvName.text = list[position].name
            ivIcon.setImageResource(list[position].icon)
        }
    }
}