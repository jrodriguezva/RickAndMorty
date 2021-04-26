package com.jrodriguezva.rickandmortykotlin.ui.character.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jrodriguezva.rickandmortykotlin.R
import com.jrodriguezva.rickandmortykotlin.databinding.ItemCharacterBinding
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Status
import com.jrodriguezva.rickandmortykotlin.ui.character.MainFragmentDirections
import com.jrodriguezva.rickandmortykotlin.utils.extensions.textColor
import com.jrodriguezva.rickandmortykotlin.utils.extensions.themeColor


class CharactersAdapter(private val onClickFavorite: (Character) -> Unit) :
    ListAdapter<Character, CharactersAdapter.CharactersViewHolder>(diffCallback) {

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
                favorite.setOnClickListener {
                    item.favorite = !item.favorite
                    startAnimation(favorite, item)
                }
                card.setOnClickListener {
                    val direction = MainFragmentDirections.actionMainFragmentToDetailCharacterFragment(item.id)
                    binding.root.findNavController().navigate(direction)
                }

                if (item.favorite)
                    favorite.setColorFilter(
                        ContextCompat.getColor(itemView.context, R.color.red_700),
                        android.graphics.PorterDuff.Mode.MULTIPLY
                    )
                else favorite.setColorFilter(
                    ContextCompat.getColor(itemView.context, R.color.grey_700),
                    android.graphics.PorterDuff.Mode.MULTIPLY
                )
                image.apply {
                    transitionName = item.image
                    load(item.image)
                }
                title.text = item.name
                status.text = item.status.name
                when (item.status) {
                    Status.ALIVE -> status.setTextColor(itemView.context.themeColor(R.attr.colorPrimary))
                    Status.DEAD -> status.textColor(R.color.red_700)
                    Status.UNKNOWN -> status.setTextColor(itemView.context.themeColor(R.attr.colorOnBackground))
                }
            }

        }
    }


    private fun startAnimation(view: ImageView, character: Character) {
        val rotationAnim = if (character.favorite) {
            ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        } else {
            ObjectAnimator.ofFloat(view, "rotation", 360f, 0f)
        }.apply {
            duration = 300
            interpolator = AccelerateInterpolator()
        }

        val bounceAnimX = ObjectAnimator.ofFloat(view, "scaleX", 0.2f, 1f).apply {
            duration = 300
            interpolator = OvershootInterpolator()
        }

        val bounceAnimY = ObjectAnimator.ofFloat(view, "scaleY", 0.2f, 1f).apply {
            duration = 300
            interpolator = OvershootInterpolator()
        }

        val colorAnim = if (character.favorite) {
            ObjectAnimator.ofArgb(
                view, "colorFilter",
                ContextCompat.getColor(view.context, R.color.grey_700),
                ContextCompat.getColor(view.context, R.color.red_700)
            )
        } else {
            ObjectAnimator.ofArgb(
                view, "colorFilter",
                ContextCompat.getColor(view.context, R.color.red_700),
                ContextCompat.getColor(view.context, R.color.grey_700)
            )
        }.apply {
            duration = 600
            interpolator = AccelerateDecelerateInterpolator()
        }

        AnimatorSet().apply {
            play(rotationAnim).with(colorAnim)
            play(bounceAnimX).with(bounceAnimY).after(rotationAnim)
            doOnEnd {
                onClickFavorite(character)
            }
            start()
        }

    }
}

