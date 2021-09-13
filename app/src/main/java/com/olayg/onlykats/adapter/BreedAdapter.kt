package com.olayg.onlykats.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olayg.onlykats.databinding.ItemBreedBinding
import com.olayg.onlykats.databinding.ItemKatBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.util.loadWithGlide
import okio.blackholeSink

class BreedAdapter(
    private val breedList: MutableList<Breed> = mutableListOf()
) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BreedViewHolder.getInstance(parent)

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
        val listSize = breedList.size
        breedList.clear()
        notifyItemRangeRemoved(0, listSize)
    }

    class BreedViewHolder(
        private val binding: ItemBreedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadBreed(breed: Breed) = with(binding) {
            breed.image?.url?.let { ivBreed.loadWithGlide(it) }
            breedName.text = breed.name
        }

        companion object {
            fun getInstance(parent: ViewGroup): BreedViewHolder {
                val binding = ItemBreedBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return BreedViewHolder(binding)
            }
        }

    }

}
