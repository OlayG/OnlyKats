package com.olayg.onlykats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.olayg.onlykats.databinding.ItemKatBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.util.loadWithGlide

class BreedAdapter(
    private val breedList: MutableList<Breed> = mutableListOf(),
    private val listener: (Breed) -> Unit
) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ) = BreedViewHolder.getInstance(parent, listener)

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) = with(holder) {
        loadBreedKat(breedList[position])
        val item = breedList[position]
        itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount() = breedList.size

    fun updateBreedList(breeds: List<Breed>) {
        if (breeds.lastOrNull() != breedList.lastOrNull()) {
            val positionStart = breedList.size
            breedList.addAll(breeds)
            notifyItemRangeInserted(positionStart, breeds.size)
        }
    }

    fun clearList() {
        val listSize = 0
        notifyItemRangeInserted(0, listSize)
    }

    class BreedViewHolder(
        private val binding: ItemKatBinding,
        private val listener: (Breed) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadBreedKat(breed: Breed) = with(binding) {
            breed.image?.url?.let { ivBreedKat.loadWithGlide(it) }
            (breed.name).also { breedNameKat.text = it }

            binding.root.setOnClickListener {
                listener.invoke(breed)
            }
        }

        companion object {
            fun getInstance(parent: ViewGroup, listener: (Breed) -> Unit): BreedViewHolder {
                val binding = ItemKatBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return BreedViewHolder(binding, listener)
            }
        }
    }
}
