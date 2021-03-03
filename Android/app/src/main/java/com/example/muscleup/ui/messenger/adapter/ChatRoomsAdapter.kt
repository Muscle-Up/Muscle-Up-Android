package com.example.muscleup.ui.messenger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muscleup.databinding.ItemFriendsBinding
import com.example.muscleup.ui.messenger.model.MessageRoom

class ChatRoomsAdapter(private val chatRooms: List<MessageRoom>) : RecyclerView.Adapter<ChatFriendsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFriendsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendsBinding.inflate(inflater, parent, false)
        return ChatFriendsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatFriendsViewHolder, position: Int) {
        holder.bind(chatRooms[position])
    }

    override fun getItemCount(): Int = chatRooms.size
}

class ChatFriendsViewHolder(private val binding: ItemFriendsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item : MessageRoom){

    }

}