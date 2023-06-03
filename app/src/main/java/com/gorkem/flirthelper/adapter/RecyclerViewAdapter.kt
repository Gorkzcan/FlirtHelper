package com.gorkem.flirthelper.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gorkem.flirthelper.databinding.ItemRowBinding
import com.gorkem.flirthelper.model.Question

class RecyclerViewAdapter(private val questionList: ArrayList<Question>)
    :RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>(){
        class RowHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.binding.textName.text = questionList[position].question


    }
}