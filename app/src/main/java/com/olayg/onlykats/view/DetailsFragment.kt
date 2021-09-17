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
        val breed = args.breedArg


        Log.i(TAG, "onCreateView in details: the value is ${breed.wikipediaUrl}) ")

        val breedInfoString  =
                "adaptability: ${breed.adaptability} \n" +
                "affectionLevel: ${breed.affectionLevel} \n" +
                "altNames: ${breed.altNames} \n" +
                "cfaUrl: ${breed.cfaUrl} \n" +
                "childFriendly: ${breed.childFriendly} \n" +
                "countryCode: ${breed.countryCode} \n" +
                "description: ${breed.description} \n" +
                "dogFriendly: ${breed.dogFriendly} \n" +
                "description: ${breed.description} \n" +
                "dogFriendly: ${breed.dogFriendly} \n" +
                "energyLevel: ${breed.energyLevel} \n" +
                "experimental: ${breed.experimental} \n" +
                "grooming: ${breed.grooming} \n" +
                "hairless: ${breed.hairless} \n" +
                "healthIssues: ${breed.healthIssues} \n" +
                "hypoallergenic: ${breed.hypoallergenic} \n" +
                "id: ${breed.id} \n" +
                "indoor: ${breed.indoor} \n" +
                "intelligence: ${breed.intelligence} \n" +
                "lap: ${breed.lap} \n" +
                "lifeSpan: ${breed.lifeSpan} \n" +
                "name: ${breed.name} \n" +
                "natural: ${breed.natural} \n" +
                        "origin: ${breed.origin} \n" +
                        "rare: ${breed.rare} \n" +
                        "referenceImageId: ${breed.referenceImageId} \n" +
                        "rex: ${breed.rex} \n" +
                        "sheddingLevel: ${breed.sheddingLevel} \n" +
                        "shortLegs: ${breed.shortLegs} \n" +
                        "socialNeeds: ${breed.socialNeeds} \n" +
                        "strangerFriendly: ${breed.strangerFriendly} \n" +
                        "suppressedTail: ${breed.suppressedTail} \n" +
                        "temperament: ${breed.temperament} \n" +
                "vcaHospitalsUrl: ${breed.vcaHospitalsUrl} \n" +
                "vetStreetUrl: ${breed.vetStreetUrl} \n" +
                "vocalisation: ${breed.vocalisation} \n" +
                "wikipediaUrl: ${breed.wikipediaUrl} \n"




        binding.textView.setText(breedInfoString);


    }.root



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}