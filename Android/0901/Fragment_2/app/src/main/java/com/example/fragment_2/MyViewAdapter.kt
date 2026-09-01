package com.example.fragment_2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewAdapter(val fragManager: FragmentManager, val lifecycle: Lifecycle) : FragmentStateAdapter(fragManager, lifecycle) {

    var fragList = mutableListOf<Fragment>()

    init {
        fragList.add(Fragment_1())
        fragList.add(Fragment_2())
        fragList.add(Fragment_3())
        fragList.add(Fragment_4())
        fragList.add(Fragment_5())
    }

    override fun createFragment(position: Int): Fragment {
        val frag = fragList.get(position)
        return frag
    }

    override fun getItemCount(): Int {
        return fragList.size
    }
}