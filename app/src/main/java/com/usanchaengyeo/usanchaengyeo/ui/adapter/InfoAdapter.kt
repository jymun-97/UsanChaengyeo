package com.usanchaengyeo.usanchaengyeo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.usanchaengyeo.usanchaengyeo.data.model.forecast.Info
import com.usanchaengyeo.usanchaengyeo.databinding.ItemInfoBinding

class InfoAdapter : ListAdapter<Info, InfoAdapter.ViewHolder>(diffUtil) {
    
    inner class ViewHolder(
        private val binding: ItemInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(info: Info) {
            binding.info = info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Info>() {
            override fun areItemsTheSame(oldItem: Info, newItem: Info): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Info, newItem: Info): Boolean {
                return oldItem == newItem
            }
        }
    }
}