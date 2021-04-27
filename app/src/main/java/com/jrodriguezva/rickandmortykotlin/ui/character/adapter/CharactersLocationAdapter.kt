package com.jrodriguezva.rickandmortykotlin.ui.character.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jrodriguezva.rickandmortykotlin.databinding.ItemCharacterLocationBinding
import com.jrodriguezva.rickandmortykotlin.domain.model.Character

class CharactersLocationAdapter : ListAdapter<Character, CharactersLocationAdapter.CharactersViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharactersViewHolder(ItemCharacterLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Character, newItem: Character) = oldItem == newItem
        }
    }

    inner class CharactersViewHolder(private val binding: ItemCharacterLocationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: Character) {
            binding.apply {
                image.load(item.image)
                name.text = item.name
            }
        }
    }
}
