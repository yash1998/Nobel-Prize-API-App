package com.example.nobelprizedummyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nobelprizedummyapp.R
import com.example.nobelprizedummyapp.constants.GlobalConstants.CATEGORIES
import com.example.nobelprizedummyapp.constants.GlobalConstants.LAUREATES_LIST
import com.example.nobelprizedummyapp.constants.GlobalConstants.PRIZE_FILTER
import com.example.nobelprizedummyapp.constants.GlobalConstants.SPECIAL_LAUREATES_LIST
import com.example.nobelprizedummyapp.databinding.FragmentNobelPrizesBinding
import com.example.nobelprizedummyapp.model.Laureates
import com.example.nobelprizedummyapp.model.PrizeFilter
import com.example.nobelprizedummyapp.model.Prizes
import com.example.nobelprizedummyapp.view.adapter.NobelPrizesAdapter
import com.example.nobelprizedummyapp.viewmodel.NobelPrizesViewModel

class NobelPrizesFragment : Fragment(), FilterFragment.IFilters {

    companion object {
        const val TAG = "NobelPrizesFragment"
    }

    private val viewModel: NobelPrizesViewModel by lazy { ViewModelProvider(this)[NobelPrizesViewModel::class.java] }
    private val adapter: NobelPrizesAdapter by lazy { NobelPrizesAdapter() }
    private lateinit var binding: FragmentNobelPrizesBinding
    private var currentFilter = PrizeFilter()
    private var totalPrizesList: List<Prizes> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNobelPrizesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        viewModel.apply {
            nobelPrizesList.observe(viewLifecycleOwner, Observer {
                setData(it.prizes)
                isLoading.value = false
            })
            isLoading.observe(viewLifecycleOwner, Observer {
                binding.loader.visibility = if (it) View.VISIBLE else View.GONE
            })
            viewModel.isLoading.value = true
            getNobelPrizesList()
        }

        binding.addFiltersButton.setOnClickListener {
            val filterFragment = FilterFragment()
            filterFragment.arguments = Bundle().apply {
                putParcelable(PRIZE_FILTER, currentFilter)
                putStringArrayList(
                    CATEGORIES,
                    ArrayList(totalPrizesList.groupBy { it.category }.keys)
                )
            }
            filterFragment.filters = this
            filterFragment.show(childFragmentManager, FilterFragment.TAG)
        }
    }

    private fun initUI() {
        binding.nobelPrizesRecyclerView.adapter = adapter
    }

    private fun setData(prizes: List<Prizes>) {
        totalPrizesList = prizes
        filterChanged(currentFilter)
    }

    override fun filterChanged(filter: PrizeFilter) {
        currentFilter = filter
        var currentPrizesList = totalPrizesList
        if (currentFilter.year != null) {
            currentPrizesList = currentPrizesList.filter { it.year == currentFilter.year }
        }
        if (currentFilter.category != null) {
            currentPrizesList = currentPrizesList.filter { it.category == currentFilter.category }
        }
        adapter.changeNobelPrizeList(currentPrizesList)
    }

    fun showSpecialLaureates() {
        val uniqueIds = HashMap<String, Laureates>()
        val specialLaureates = HashMap<String, ArrayList<Prizes>>()
        totalPrizesList.forEach { prize ->
            prize.laureates?.forEach {
                uniqueIds[it.id] = it
                specialLaureates[it.id] = ArrayList()
            }
        }
        totalPrizesList.forEach { prize ->
            prize.laureates?.forEach { laureate ->
                specialLaureates[laureate.id]?.add(prize)
            }
        }
        specialLaureates.entries.removeIf { entry ->
            entry.value.size == 1
        }
        val laureatesList = ArrayList<Laureates>()

        specialLaureates.forEach {
            uniqueIds[it.key]?.let { laureatesList.add(it) }
        }
        val specialLaureatesFragment = SpecialLaureatesFragment()
        specialLaureatesFragment.arguments = Bundle().apply {
            putSerializable(SPECIAL_LAUREATES_LIST, specialLaureates)
            putSerializable(LAUREATES_LIST, laureatesList)
        }
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.container,
                specialLaureatesFragment,
                SpecialLaureatesFragment.TAG
            )
            ?.addToBackStack(SpecialLaureatesFragment.TAG)
            ?.commit()
    }
}