package com.example.nobelprizedummyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.nobelprizedummyapp.R
import com.example.nobelprizedummyapp.constants.GlobalConstants.CATEGORIES
import com.example.nobelprizedummyapp.constants.GlobalConstants.PRIZE_FILTER
import com.example.nobelprizedummyapp.databinding.FragmentFilterBinding
import com.example.nobelprizedummyapp.model.PrizeFilter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "FilterFragment"
    }

    private lateinit var binding: FragmentFilterBinding
    lateinit var filters: IFilters
    private var prizeFilter: PrizeFilter? = null
    private var categories: ArrayList<String>? = null
    private var years = ArrayList((1990..2020).map { "$it" })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prizeFilter = arguments?.getParcelable(PRIZE_FILTER)
        categories = arguments?.getStringArrayList(CATEGORIES)

        years.add(0, getString(R.string.choose_year_text))
        binding.apply {
            context?.let {
                yearSpinner.adapter =
                    ArrayAdapter(it, R.layout.custom_spinner_item, years)
            }
            yearSpinner.setSelection(years.indexOf(prizeFilter?.year))

            categories?.add(0, getString(R.string.choose_category_text))
            categories?.indexOf(prizeFilter?.category)
                ?.let { binding.categorySpinner.setSelection(it) }
            context?.let {
                categorySpinner.adapter =
                    ArrayAdapter(it, R.layout.custom_spinner_item, categories ?: listOf())
            }

            yearSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position == 0) {
                            prizeFilter?.year = null
                        } else {
                            prizeFilter?.year = years[position]
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        prizeFilter?.year = null
                    }
                }
            categorySpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (position == 0) {
                            prizeFilter?.category = null
                        } else {
                            prizeFilter?.category = categories?.get(position)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        prizeFilter?.category = null
                    }
                }

            applyButton.setOnClickListener {
                updateFilters()
            }

            resetFiltersButton.setOnClickListener {
                resetFilters()
            }
        }
    }

    private fun updateFilters() {
        prizeFilter?.let {
            filters.filterChanged(it)
            dismiss()
        }
    }

    private fun resetFilters() {
        prizeFilter?.year = null
        prizeFilter?.category = null
        updateFilters()
    }

    interface IFilters {
        fun filterChanged(filter: PrizeFilter)
    }
}