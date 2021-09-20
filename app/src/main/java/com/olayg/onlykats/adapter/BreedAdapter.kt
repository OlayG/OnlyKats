package com.olayg.onlykats.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.olayg.onlykats.databinding.ItemBreedBinding
import com.olayg.onlykats.databinding.ItemKatBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.util.loadBreedsWithGlide
import com.olayg.onlykats.util.loadSingleImage
import com.olayg.onlykats.view.BrowseFragmentDirections
import kotlinx.android.parcel.Parcelize

/**
 * ListView - loads all objects into memory
 * RecyclerView - Leverages the ViewHolder Pattern to optimizing scrolling and memory consumption
 * ListAdapter - Same as Recyclerview but we don't have to use the notify methods to update the adapter
 */
// TODO: 9/11/21 Setup breed adapter to display list of breeds
// TODO: 9/11/21 update the clear method
class BreedAdapter(
    private val breedList: MutableList<Breed> = mutableListOf(),

) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BreedViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.loadBreed(breedList[position])
    }


    override fun getItemCount() = breedList.size

    fun updateList(breeds: List<Breed>) {
        Log.e("updateList", "Inside Breeds updatlist ")
        if (breeds.lastOrNull() != breedList.lastOrNull()) {
            val positionStart = breedList.size
            breedList.addAll(breeds)
            notifyItemRangeInserted(positionStart, breeds.size)
        }
    }

    fun clear() {
        val listSize = breedList.size
        breedList.clear()
        notifyItemRangeRemoved(0, listSize)
    }


    class BreedViewHolder(
        private val binding: ItemBreedBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun loadBreed(bred: Breed) = with(binding)
        {
            ivBreeds.loadSingleImage(bred?.image?.url)
            breedNames.text = bred.name ?: "Unknown"
            ivBreeds.setOnClickListener() {
                it.findNavController()
                    .navigate(
                        BrowseFragmentDirections.actionDetailsFragment(Breed(bred.adaptability, bred.affectionLevel, bred.altNames, bred.cfaUrl, bred.childFriendly, bred.countryCode, bred.countryCodes, bred.description, bred.dogFriendly, bred.energyLevel, bred.experimental, bred.grooming, bred.hairless, bred.healthIssues, bred.hypoallergenic, bred.referenceImageId, bred.image, bred.indoor, bred.intelligence, bred.lap, bred.lifeSpan, bred.name, bred.natural, bred.origin, bred.rare, bred.referenceImageId, bred.rex, bred.sheddingLevel, bred.shortLegs, bred.socialNeeds, bred.strangerFriendly, bred.suppressedTail, bred.temperament, bred.cfaUrl, bred.vcaHospitalsUrl, bred.vetStreetUrl, bred.vocalisation, bred.weight, bred.wikipediaUrl)
                        )
                    )
            }
        }


        companion object {
            fun getInstance(parent: ViewGroup): BreedViewHolder {
                val binding =
                    ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return BreedViewHolder(binding)
            }
        }
    }
}