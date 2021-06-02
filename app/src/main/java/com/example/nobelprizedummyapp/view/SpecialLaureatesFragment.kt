package com.example.nobelprizedummyapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nobelprizedummyapp.constants.GlobalConstants.LAUREATES_LIST
import com.example.nobelprizedummyapp.constants.GlobalConstants.SPECIAL_LAUREATES_LIST
import com.example.nobelprizedummyapp.databinding.FragmentSpecialLaureatesBinding
import com.example.nobelprizedummyapp.model.Laureates
import com.example.nobelprizedummyapp.model.Prizes
import com.example.nobelprizedummyapp.view.adapter.LaureatesAdapter

class SpecialLaureatesFragment : Fragment() {

    companion object {
        const val TAG = "SpecialLaureatesFragment"
    }

    private lateinit var binding: FragmentSpecialLaureatesBinding
    private var specialLaureates: HashMap<String, ArrayList<Prizes>>? = null
    private var laureatesList: List<Laureates>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpecialLaureatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        specialLaureates =
            arguments?.getSerializable(SPECIAL_LAUREATES_LIST) as? HashMap<String, ArrayList<Prizes>>

        laureatesList = arguments?.getSerializable(LAUREATES_LIST) as? List<Laureates>

        binding.specialLaureatesList.adapter =
            LaureatesAdapter(
                laureatesList,
                specialLaureates?.values?.toList()
            )
    }

}