package com.example.openinapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.openinapp.fragments.RecentLInkFragment
import com.example.openinapp.fragments.TopLinkFragment

class ViewPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){

    private val fragments = listOf(TopLinkFragment(), RecentLInkFragment())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}