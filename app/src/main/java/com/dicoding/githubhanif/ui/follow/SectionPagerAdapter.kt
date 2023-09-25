package com.dicoding.githubhanif.ui.follow

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(
    frag: FragmentActivity,
    private val fragment: MutableList<Fragment>
) : FragmentStateAdapter(frag) {
    override fun getItemCount(): Int = fragment.size
    override fun createFragment(position: Int): Fragment = fragment[position]
}