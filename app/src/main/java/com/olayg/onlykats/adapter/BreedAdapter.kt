package com.olayg.onlykats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olayg.onlykats.databinding.ItemBreedBinding
import com.olayg.onlykats.databinding.ItemKatBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.util.loadWithGlide


/**
 * ListView - loads all objects into memory
 * RecyclerView - Leverages the ViewHolder Pattern to optimizing scrolling and memory consumption
 * ListAdapter - Same as Recyclerview but we don't have to use the notify methods to update the adapter
 */
// TODO: 9/11/21 Setup breed adapter to display list of breeds
// TODO: 9/11/21 update the clear method
class BreedAdapter(

    private val breedList: MutableList<Breed> = mutableListOf()
) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = BreedViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.loadBreed(breedList[position])
    }

    override fun getItemCount() = breedList.size

    fun updateList(breeds: List<Breed>) {
        if (breeds.lastOrNull() != breedList.lastOrNull()) {
            val positionStart = breedList.size
            breedList.addAll(breeds)
            notifyItemRangeInserted(positionStart, breeds.size)
        }
    }

    fun clear() {
        val listSize = 0
        notifyItemRangeRemoved(0, listSize)
    }


    class BreedViewHolder(

        private val binding: ItemBreedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadBreed(breed: Breed) = with(binding) {
//            tvBreed.text = breed.name
            ivBreed.loadWithGlide(breed.image?.url)
        }

        companion object {
            fun getInstance(parent: ViewGroup): BreedAdapter.BreedViewHolder {
                val binding = ItemBreedBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return BreedViewHolder(binding)
            }
        }

    }

}
