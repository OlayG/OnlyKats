package com.olayg.onlykats.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
    }

}