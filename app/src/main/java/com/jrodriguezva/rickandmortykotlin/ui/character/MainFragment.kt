package com.jrodriguezva.rickandmortykotlin.ui.character

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jrodriguezva.rickandmortykotlin.R
import com.jrodriguezva.rickandmortykotlin.ui.utils.extensions.endless
import com.jrodriguezva.rickandmortykotlin.ui.utils.extensions.visible
import com.jrodriguezva.rickandmortykotlin.databinding.MainFragmentBinding
import com.jrodriguezva.rickandmortykotlin.ui.character.adapter.CharactersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel: MainViewModel by viewModels()

    private var fragmentBinding: MainFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = MainFragmentBinding.bind(view)

        val adapter = CharactersAdapter(viewModel::onClickFavorite)

        fragmentBinding?.run {

            viewModel.spinner.onEach {
                Log.d("TAG", "onViewCreated: $it")
                progress.visible = it
            }.launchIn(lifecycleScope)
            viewModel.characters.onEach { adapter.submitList(it) }.launchIn(lifecycleScope)

            recycler.endless { viewModel.getNextPage() }
            recycler.adapter = adapter
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

}