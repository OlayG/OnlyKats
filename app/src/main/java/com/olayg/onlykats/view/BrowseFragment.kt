package com.olayg.onlykats.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentBrowseBinding
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.util.ApiState
import com.olayg.onlykats.view.adapter.KatAdapter
import com.olayg.onlykats.viewmodel.KatViewModel

/**
 * A simple [Fragment] subclass.
 */
class BrowseFragment : Fragment(R.layout.fragment_browse) {

    private lateinit var binding: FragmentBrowseBinding

    private val katViewModel by activityViewModels<KatViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBrowseBinding.bind(view)
        initViews()
        setupObservers()
    }

    // with(receiver) is 1 of 5 scope functions
    private fun initViews() = with(binding) {
        rvKats.adapter = KatAdapter()
        rootNestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == (v as NestedScrollView).getChildAt(0).measuredHeight - v.getMeasuredHeight()) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                katViewModel.page++
                // on below line we are making our progress bar visible.
                binding.pbLoading.isVisible = true
            }
        }
    }

    private fun setupObservers() = with(katViewModel) {
        katState.observe(viewLifecycleOwner) { state ->
            binding.pbLoading.isVisible = state is ApiState.Loading
            if (state is ApiState.Success) handleSuccess(state.data)
            if (state is ApiState.Failure) handleFailure(state.errorMsg)
        }
    }

    private fun handleSuccess(kats: List<Kat>) {
        Log.d(TAG, "ApiState.Success: $kats")
        (binding.rvKats.adapter as KatAdapter).updateList(kats)
    }

    private fun handleFailure(errorMsg: String) {
        Log.d(TAG, "ApiState.Failure: $errorMsg")
    }

    companion object {
        private const val TAG = "BrowseFragment"
    }
}