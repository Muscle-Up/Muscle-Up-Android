package com.example.muscleup.qna

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muscleup.R
import com.example.muscleup.qna.model.QnA

class QnaAdapter(private val qnas : List<QnA>) : RecyclerView.Adapter<QnaAdapter.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(view: View){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_qna,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return qnas.size

    }

}