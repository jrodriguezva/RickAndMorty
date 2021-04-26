package com.jrodriguezva.rickandmortykotlin.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jrodriguezva.rickandmortykotlin.R
import com.jrodriguezva.rickandmortykotlin.databinding.FavoriteFragmentBinding
import com.jrodriguezva.rickandmortykotlin.ui.favorite.adapter.FavoriteCharactersAdapter
import com.jrodriguezva.rickandmortykotlin.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.favorite_fragment) {

    private val viewModel: FavoriteViewModel by viewModels()

    private var fragmentBinding: FavoriteFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = FavoriteFragmentBinding.bind(view)
        val adapter = FavoriteCharactersAdapter(viewModel::onClickFavorite)

        fragmentBinding?.run {
            viewModel.characters.onEach {
                emptyViewVisibility(it.isEmpty())
                adapter.submitList(it)
            }.launchIn(lifecycleScope)
            recycler.adapter = adapter
        }
    }

    private fun emptyViewVisibility(visible: Boolean) {
        fragmentBinding?.emptyGroup?.visible = visible
        fragmentBinding?.recycler?.visible = !visible
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}

