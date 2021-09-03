package com.olayg.onlykats.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentBrowseBinding

/**
 * A simple [Fragment] subclass.
 */
class BrowseFragment : Fragment(R.layout.fragment_browse) {

    private lateinit var binding: FragmentBrowseBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBrowseBinding.bind(view)
        initViews()
    }

    // with(receiver) is 1 of 5 scope functions
    private fun initViews() = with(binding) {
        btnDetails.setOnClickListener {
            /*
            If we arent using safeArgs we can directly call the action id
            findNavController().navigate(R.id.action_detailsFragment)
            */
            val direction = BrowseFragmentDirections.actionDetailsFragment()
            findNavController().navigate(direction)

        }
        btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment)
        }
    }

}