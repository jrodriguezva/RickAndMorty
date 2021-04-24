package com.jrodriguezva.rickandmortykotlin.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jrodriguezva.rickandmortykotlin.R
import com.jrodriguezva.rickandmortykotlin.com.jrodriguezva.rickandmortykotlin.ui.utils.extensions.endless
import com.jrodriguezva.rickandmortykotlin.com.jrodriguezva.rickandmortykotlin.ui.utils.extensions.visible
import com.jrodriguezva.rickandmortykotlin.databinding.MainFragmentBinding
import com.jrodriguezva.rickandmortykotlin.ui.main.adapter.CharactersAdapter
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

        val adapter = CharactersAdapter()

        fragmentBinding?.run {

            viewModel.spinner.onEach { progress.visible = it }.launchIn(lifecycleScope)
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