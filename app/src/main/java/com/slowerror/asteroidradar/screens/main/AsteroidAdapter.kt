package com.slowerror.asteroidradar.screens.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.slowerror.asteroidradar.databinding.ItemAsteroidBinding
import com.slowerror.asteroidradar.models.Asteroid

class AsteroidAdapter(private val clickListener: AsteroidListener) :
    ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.bind(asteroid, clickListener)
    }


    class AsteroidViewHolder private constructor(private val binding: ItemAsteroidBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid, clickListener: AsteroidListener) {
            binding.asteroid = asteroid
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemAsteroidBinding.inflate(inflater, parent, false)
                return AsteroidViewHolder(binding)
            }
        }
    }

}

class AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {

    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}

class AsteroidListener(val clickListener: (asteroid: Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) = clickListener(asteroid)
}