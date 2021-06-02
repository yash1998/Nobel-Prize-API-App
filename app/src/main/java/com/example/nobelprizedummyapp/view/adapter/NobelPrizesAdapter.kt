package com.example.nobelprizedummyapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nobelprizedummyapp.databinding.LayoutPrizeItemBinding
import com.example.nobelprizedummyapp.model.Prizes

class NobelPrizesAdapter : RecyclerView.Adapter<NobelPrizesAdapter.PrizeViewHolder>() {

    private lateinit var binding: LayoutPrizeItemBinding
    private var nobelPrizesList: List<Prizes>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrizeViewHolder {
        binding = LayoutPrizeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrizeViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: PrizeViewHolder, position: Int) {
        val item = nobelPrizesList?.get(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return nobelPrizesList?.size ?: 0
    }

    fun changeNobelPrizeList(list: List<Prizes>?) {
        nobelPrizesList = list
        notifyDataSetChanged()
    }

    class PrizeViewHolder(private val itemBinding: LayoutPrizeItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(prizes: Prizes?) {
            itemBinding.yearTextView.text = prizes?.year
            itemBinding.categoryTextView.text = prizes?.category
            itemBinding.laureatesList.adapter =
                LaureatesAdapter(
                    prizes?.laureates
                )
        }
    }
}