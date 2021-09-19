package com.olayg.onlykats.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentDetailBinding
import com.olayg.onlykats.util.loadWithGlide

class DetailsFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ACTION BAR
        setHasOptionsMenu(true)
        // CLASS METHODS
        loadKatDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar_menu, menu)
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title = "${args.details.name} Details"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.back) {
            findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadKatDetails() = with(binding) {
        args.details.image?.url?.let { ivKatDetail.loadWithGlide(it) }
        lifeSpanKat.text = args.details.lifeSpan
        energyLevel.rating = args.details.energyLevel?.toFloat()!!
        socialNeeds.rating = args.details.socialNeeds?.toFloat()!!
        (args.details.vetStreetUrl).also { vetStreetUrlKat.text = it }
        (args.details.wikipediaUrl).also { wikipediaUrlKat.text = it }
    }
}