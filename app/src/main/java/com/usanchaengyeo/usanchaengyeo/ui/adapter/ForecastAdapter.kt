package com.usanchaengyeo.usanchaengyeo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.usanchaengyeo.usanchaengyeo.data.model.forecast.Forecast
import com.usanchaengyeo.usanchaengyeo.databinding.ItemForecastBinding

class ForecastAdapter : ListAdapter<Forecast, ForecastAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(
        private val binding: ItemForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(forecast: Forecast) {
            binding.forecast = forecast
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemForecastBinding.inflate(
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
        val diffUtil = object : DiffUtil.ItemCallback<Forecast>() {
            override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return oldItem.fcstTime == newItem.fcstTime
            }

            override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
                return oldItem == newItem
            }
        }
    }
}