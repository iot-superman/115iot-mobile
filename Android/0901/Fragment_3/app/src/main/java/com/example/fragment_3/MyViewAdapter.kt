package com.example.fragment_3

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewAdapter(fragManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragManager, lifecycle) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Fragment_1()
            1 -> Fragment_2()
            2 -> Fragment_3()
            3 -> Fragment_4()
            4 -> Fragment_5()
            else -> Fragment_1()
        }
    }
}
