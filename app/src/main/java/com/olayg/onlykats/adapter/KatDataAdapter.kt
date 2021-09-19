package com.olayg.onlykats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.olayg.onlykats.model.room.KatData
import com.olayg.onlykats.databinding.ItemKatDataBinding

class KatDataAdapter(
    private val listener: (KatData) -> Unit
) : RecyclerView.Adapter<KatDataAdapter.KatDataViewHolder>() {

    private var katDataList = emptyList<KatData>()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = KatDataViewHolder.getInstance(parent, listener)

    override fun onBindViewHolder(holder: KatDataViewHolder, position: Int) {
        holder.loadKatData(katDataList[position])
        val item = katDataList[position]
        holder.itemView.setOnClickListener { listener(item) }
    }

    override fun getItemCount() = katDataList.size

    fun setData(katData: List<KatData>) {
        this.katDataList = katData
    }

    class KatDataViewHolder(
        private val binding: ItemKatDataBinding,
        private val listener: (KatData) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun loadKatData(katData: KatData) = with(binding) {
            katDataId.text = katData.id.toString()
            katDataName.text = katData.name
            katDataAge.text = katData.age.toString()

            root.setOnClickListener {
                listener.invoke(katData)
            }
        }

        companion object {
            fun getInstance(parent: ViewGroup, listener: (KatData) -> Unit): KatDataViewHolder {
                val binding = ItemKatDataBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return KatDataViewHolder(binding, listener)
            }
        }
    }
}
