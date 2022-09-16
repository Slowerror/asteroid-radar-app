package com.slowerror.asteroidradar.screens.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.slowerror.asteroidradar.databinding.ItemAsteroidBinding
import com.slowerror.asteroidradar.models.Asteroid

class AsteroidAdapter : RecyclerView.Adapter<AsteroidAdapter.AsteroidViewHolder>() {

    private var asteroidsList = listOf<Asteroid>()

    fun setAsteroid(data: List<Asteroid>) {
        this.asteroidsList = data
        notifyDataSetChanged()
    }

    class AsteroidViewHolder(var binding: ItemAsteroidBinding)
        : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAsteroidBinding.inflate(inflater, parent, false)

        return AsteroidViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = asteroidsList[position]
        holder.binding.asteroid = asteroid
    }

    override fun getItemCount(): Int = asteroidsList.size

}