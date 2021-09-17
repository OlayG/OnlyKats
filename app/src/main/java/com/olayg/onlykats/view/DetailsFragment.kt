package com.olayg.onlykats.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentDetailBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.util.loadWithGlide
import com.olayg.onlykats.viewmodel.KatViewModel

class DetailsFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val katViewModel by activityViewModels<KatViewModel>()
    private val args : DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        val selectedBreed = args.selectedBreed
        selectedBreed.image?.url?.let { ivDetail.loadWithGlide(it) }
        displayText(selectedBreed)
    }

    private fun displayText(breed: Breed) = with(binding) {
        detailName.text = breed.name
        detailMoreInfo.text = "more info"
        detailLinks.text = "links"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}