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
import android.text.method.LinkMovementMethod

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
        binding.btnBackToBrowse.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_detailsFragment_to_browseFragment)
        }
    }

    private fun loadKatDetails() = with(binding) {
        val wikiUrl = wikipediaUrlKat
        wikiUrl.movementMethod = LinkMovementMethod.getInstance()
        args.details.image?.url?.let { ivKatDetail.loadWithGlide(it) }
        breedNameKat.text = args.details.name
        ("Lifespan: " + args.details.lifeSpan + " Years ").also { lifeSpanKat.text = it }
        ("Energy Level: " + args.details.energyLevel).also { energyLevelKat.text = it }
        ("Shedding Level: " + args.details.sheddingLevel).also { sheddingLevelKat.text = it }
        ("Social Needs: " + args.details.socialNeeds).also { socialNeedsKat.text = it }
        (args.details.vetStreetUrl).also { vetStreetUrlKat.text = it }
        (args.details.wikipediaUrl).also { wikiUrl.text = it }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}