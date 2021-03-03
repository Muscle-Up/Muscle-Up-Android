package com.example.muscleup.ui.messenger.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.muscleup.ui.messenger.fragment.ChattingRoomsFragment
import com.example.muscleup.ui.messenger.fragment.MessengerSearchFragment

class MessengerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                ChattingRoomsFragment()
            }
            1 -> {
                MessengerSearchFragment()
            }
            else -> ChattingRoomsFragment()

        }
    }
}