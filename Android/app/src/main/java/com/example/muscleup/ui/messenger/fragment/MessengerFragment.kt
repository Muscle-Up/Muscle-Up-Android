package com.example.muscleup.ui.messenger.fragment

import android.os.Bundle
import android.view.View
import com.example.muscleup.R
import com.example.muscleup.databinding.FragmentMessengerBinding
import com.example.muscleup.ui.messenger.adapter.MessengerAdapter
import com.example.muscleup.ui.messenger.base.BaseDataBindingFragment
import com.google.android.material.tabs.TabLayoutMediator


class MessengerFragment : BaseDataBindingFragment<FragmentMessengerBinding>() {
    override val layoutId: Int = R.layout.fragment_messenger

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.messengerViewpager.adapter = MessengerAdapter(this)
        TabLayoutMediator(binding.messengerTabLayout, binding.messengerViewpager) { tab , position->
            when(position){
                0-> {
                    tab.text = "채팅"
                }
                1-> {

                    tab.text = "검색"
                }
            }
        }.attach()
    }
}