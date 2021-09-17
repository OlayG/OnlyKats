package com.olayg.onlykats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs

import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentDetailBinding
import com.olayg.onlykats.util.loadWithGlide

class DetailsFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)

        loadKatDetails()
        handleBackToBrowse()
    }
    // As we navigate to the details fragment, pass the detail args as well to display the details
    private fun loadKatDetails() = with(binding) {
        args.details.image?.url?.let { ivKatDetail.loadWithGlide(it) }
        breedNameKat.text = args.details.name
        lifeSpanKat.text = args.details.lifeSpan
        energyLevel.rating = args.details.energyLevel?.toFloat()!!
        socialNeeds.rating = args.details.socialNeeds?.toFloat()!!
        (args.details.vetStreetUrl).also { vetStreetUrlKat.text = it }
        (args.details.wikipediaUrl).also { wikipediaUrlKat.text = it }
    }

    private fun handleBackToBrowse() {
        // Provide functionality for thr back button in the details fragment
        binding.btnBackToBrowse.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailsFragment_to_browseFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}