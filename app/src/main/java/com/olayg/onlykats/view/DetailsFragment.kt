package com.olayg.onlykats.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentDetailBinding
import com.olayg.onlykats.model.Breed


class DetailsFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    val args: DetailsFragmentArgs by navArgs()
    private  val TAG = "DetailsFragment"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailBinding.inflate(layoutInflater, container, false).also {
        _binding = it
         val myTestArg = args.breedArg

        Log.i(TAG, "onCreateView: the value is ${myTestArg}) ")

    }.root



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}