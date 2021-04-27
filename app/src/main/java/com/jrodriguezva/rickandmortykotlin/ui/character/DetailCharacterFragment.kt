package com.jrodriguezva.rickandmortykotlin.ui.character

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.jrodriguezva.rickandmortykotlin.R
import com.jrodriguezva.rickandmortykotlin.databinding.DetailCharacterFragmentBinding
import com.jrodriguezva.rickandmortykotlin.domain.model.Character
import com.jrodriguezva.rickandmortykotlin.domain.model.Location
import com.jrodriguezva.rickandmortykotlin.domain.model.Status
import com.jrodriguezva.rickandmortykotlin.ui.character.adapter.CharactersLocationAdapter
import com.jrodriguezva.rickandmortykotlin.ui.main.MainActivity
import com.jrodriguezva.rickandmortykotlin.utils.extensions.setTextViewDrawableColor
import com.jrodriguezva.rickandmortykotlin.utils.extensions.textColor
import com.jrodriguezva.rickandmortykotlin.utils.extensions.themeColor
import com.jrodriguezva.rickandmortykotlin.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale

@AndroidEntryPoint
class DetailCharacterFragment : Fragment(R.layout.detail_character_fragment) {

    private val viewModel: DetailViewModel by viewModels()
    private var fragmentBinding: DetailCharacterFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentBinding = DetailCharacterFragmentBinding.bind(view)
        fragmentBinding?.run {
            val navController = findNavController()
            toolbar.setupWithNavController(navController)

            val adapter = CharactersLocationAdapter()

            viewModel.spinner.onEach {
                progress.visible = it
                locationCard.visible = !it
            }.launchIn(lifecycleScope)
            viewModel.location.observe(viewLifecycleOwner) {
                setLocationDetail(it)
            }
            viewModel.character.onEach {
                viewModel.getLastLocation(it.location.id)
                setCharacterDetail(it)
            }.launchIn(lifecycleScope)

            viewModel.charactersLocation.onEach {
                adapter.submitList(it)
            }.launchIn(lifecycleScope)
            recyclerPeople.adapter = adapter
        }
    }

    private fun DetailCharacterFragmentBinding.setLocationDetail(it: Location) {
        locationName.text = it.name
        type.text = it.type
        dimension.text = it.dimension
    }

    private fun DetailCharacterFragmentBinding.setCharacterDetail(it: Character) {
        image.apply {
            load(it.image)
        }
        name.text = it.name
        status.text = it.status.name
        specie.text = it.species
        gender.text = it.gender.name.capitalize(Locale.getDefault())
        when (it.status) {
            Status.ALIVE -> {
                status.setTextViewDrawableColor(requireContext().themeColor(R.attr.colorPrimary))
                status.setTextColor(requireContext().themeColor(R.attr.colorPrimary))
            }
            Status.DEAD -> {
                status.setTextViewDrawableColor(ContextCompat.getColor(requireContext(), R.color.red_700))
                status.textColor(R.color.red_700)
            }
            Status.UNKNOWN -> {
                status.setTextViewDrawableColor(requireContext().themeColor(R.attr.colorOnBackground))
                status.setTextColor(requireContext().themeColor(R.attr.colorOnBackground))
            }
        }
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }

    override fun onAttach(context: Context) {
        (activity as MainActivity).visibilityBottomNavigation(false)
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).visibilityBottomNavigation(true)
    }
}
