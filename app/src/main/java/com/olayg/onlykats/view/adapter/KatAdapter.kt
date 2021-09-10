package com.olayg.onlykats.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olayg.onlykats.databinding.ItemKatBinding
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.util.loadWithGlide

/**
 * ListView - loads all objects into memory
 * RecyclerView - Leverages the ViewHolder Pattern to optimizing scrolling and memory consumption
 * ListAdapter - Same as Recyclerview but we don't have to use the notify methods to update the adapter
 */
class KatAdapter(
    private val katList: MutableList<Kat> = mutableListOf()
) : RecyclerView.Adapter<KatAdapter.KatViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = KatViewHolder.getInstance(parent)

    override fun onBindViewHolder(holder: KatViewHolder, position: Int) {
        holder.loadKat(katList[position])
    }

    override fun getItemCount() = katList.size

    fun updateList(kats: List<Kat>) {
        val positionStart = katList.size
        katList.addAll(kats)
        notifyItemRangeInserted(positionStart, kats.size)
    }

    class KatViewHolder(
        private val binding: ItemKatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadKat(kat: Kat) = with(binding) {
            ivKat.loadWithGlide(kat.url)
        }

        companion object {
            fun getInstance(parent: ViewGroup): KatViewHolder {
                val binding = ItemKatBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return KatViewHolder(binding)
            }
        }
    }
}
