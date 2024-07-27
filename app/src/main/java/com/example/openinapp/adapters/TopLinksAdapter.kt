package com.example.openinapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.openinapp.databinding.ItemViewLinksBinding
import com.example.openinapp.models.TopLink

class TopLinksAdapter(var links: List<TopLink>): RecyclerView.Adapter<TopLinksAdapter.TopLinkViewHolder>() {
    class TopLinkViewHolder(val binding: ItemViewLinksBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopLinkViewHolder {
        return TopLinkViewHolder(ItemViewLinksBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return links.size
    }

    override fun onBindViewHolder(holder: TopLinkViewHolder, position: Int) {
        holder.binding.apply {
            tvLinkUrl.text = links[position].web_link
            tvLinkName.text = links[position].title
            tvLinkDate.text = links[position].created_at
            tvLinkClicks.text = links[position].total_clicks.toString()
            Glide.with(holder.itemView.context)
                .load(links[position].original_image)
                .into(ivLink)
        }
    }
}