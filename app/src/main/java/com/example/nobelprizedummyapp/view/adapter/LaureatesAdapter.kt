package com.example.nobelprizedummyapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nobelprizedummyapp.R
import com.example.nobelprizedummyapp.databinding.LayoutLaureateItemBinding
import com.example.nobelprizedummyapp.model.Laureates
import com.example.nobelprizedummyapp.model.Prizes

class LaureatesAdapter(
    private val laureatesList: List<Laureates>? = null,
    private val achievementsList: List<List<Prizes>>? = null
) :
    RecyclerView.Adapter<LaureatesAdapter.LaureatesViewHolder>() {

    private lateinit var binding: LayoutLaureateItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaureatesViewHolder {
        binding = LayoutLaureateItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return LaureatesViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: LaureatesViewHolder, position: Int) {
        val item = laureatesList?.get(position)
        holder.apply {
            bind(item)
            binding.prizesList.apply {
                adapter =
                    SpecialLaureatesAdapter(
                        item,
                        achievementsList?.get(position)
                    )
                visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return laureatesList?.size ?: 0
    }

    class LaureatesViewHolder(val binding: LayoutLaureateItemBinding) :
        RecyclerView.ViewHolder(binding.root.rootView) {
        fun bind(laureates: Laureates?) {
            binding.apply {
                laureateNameTextView.text = String.format(
                    root.context.getString(R.string.laureate_full_name),
                    laureates?.firstname.orEmpty(),
                    laureates?.surname.orEmpty()
                )
                laureateMotivationTextView.text = laureates?.motivation
                root.setOnClickListener {
                    laureateMotivationTextView.apply {
                        visibility = if (visibility == View.VISIBLE) View.GONE else View.VISIBLE
                    }
                }
            }
        }
    }
}