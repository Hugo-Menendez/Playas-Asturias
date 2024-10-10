package com.hugo.playas.ui

import Playa
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.hugo.playas.databinding.ListItemBinding

class PlayaListAdapter (private val onClickListener:(Playa)->Unit): ListAdapter<Playa, PlayaHolder>(Playa.DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayaHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return PlayaHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PlayaHolder, position: Int) {
        val playa = getItem(position)
        holder.bind(playa, onClickListener)
    }
}
