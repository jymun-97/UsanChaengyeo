package com.usanchaengyeo.usanchaengyeo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.usanchaengyeo.usanchaengyeo.data.model.Address
import com.usanchaengyeo.usanchaengyeo.databinding.ItemAddressBinding

class AddressAdapter(
    private val itemClicked: (String) -> Unit
) : ListAdapter<Address, AddressAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(
        private val binding: ItemAddressBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(address: Address) {
            binding.address = address
            binding.root.setOnClickListener {
                itemClicked(
                    if (address.roadAddressName.isNullOrEmpty()) address.placeName
                    else address.roadAddressName
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAddressBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<Address>() {
            override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean {
                return oldItem.roadAddressName == newItem.roadAddressName
            }

            override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean {
                return oldItem == newItem
            }

        }
    }
}