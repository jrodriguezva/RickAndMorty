package com.jrodriguezva.rickandmortykotlin.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jrodriguezva.rickandmortykotlin.R
import com.jrodriguezva.rickandmortykotlin.databinding.ItemCharacterBinding
import com.jrodriguezva.rickandmortykotlin.domain.model.Character

class CharactersAdapter : ListAdapter<Character, CharactersAdapter.CharactersViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CharactersViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(item: Character) {
            binding.apply {

                card.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.down_to_up)
                card.setOnClickListener {
//                    val direction = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(
//                        item.id.toInt()
//                    )
//                    binding.root.findNavController().navigate(direction)
                }
                image.load(item.image)
                title.text = item.name
                date.text = item.id.toString()
            }

        }
    }
}

