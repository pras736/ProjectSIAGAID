package com.example.project_prasetio.tutorial

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * TutorialPagerAdapter — Adapter ViewPager2 untuk mengelola slide tutorial (Pertemuan 9 Tahap 5.2)
 */
class TutorialPagerAdapter(
    activity: FragmentActivity,
    private val fragments: List<Fragment>
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
