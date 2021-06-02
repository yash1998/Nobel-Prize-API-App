package com.example.nobelprizedummyapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nobelprizedummyapp.databinding.LayoutPrizeDetailsBinding
import com.example.nobelprizedummyapp.model.Laureates
import com.example.nobelprizedummyapp.model.Prizes

class SpecialLaureatesAdapter(
    private val laureate: Laureates?,
    private val achievementsList: List<Prizes>?
) :
    RecyclerView.Adapter<SpecialLaureatesAdapter.SpecialLaureatesViewHolder>() {

    private lateinit var binding: LayoutPrizeDetailsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialLaureatesViewHolder {
        binding =
            LayoutPrizeDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecialLaureatesViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: SpecialLaureatesViewHolder, position: Int) {
        val item = achievementsList?.get(position)
        holder.bind(laureate, item)
    }

    override fun getItemCount(): Int {
        return achievementsList?.size ?: 0
    }

    class SpecialLaureatesViewHolder(private val itemBinding: LayoutPrizeDetailsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(laureate: Laureates?, prizes: Prizes?) {
            itemBinding.apply {
                yearTextView.text = prizes?.year
                categoryTextView.text = prizes?.category
                laureateMotivationTextView.text =
                    prizes?.laureates?.find { it.id == laureate?.id }?.motivation
                root.setOnClickListener {
                    laureateMotivationTextView.apply {
                        visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
                    }
                }
            }
        }
    }
}