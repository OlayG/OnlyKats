package com.olayg.onlykats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olayg.onlykats.databinding.ItemKatBinding
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.util.loadWithGlide

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
        if (kats.lastOrNull() != katList.lastOrNull()) {
            val positionStart = katList.size
            katList.addAll(kats)
            notifyItemRangeInserted(positionStart, kats.size)
        }
    }

    fun clear() {
        val listSize = katList.size
        katList.clear()
        notifyItemRangeRemoved(0, listSize)
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
