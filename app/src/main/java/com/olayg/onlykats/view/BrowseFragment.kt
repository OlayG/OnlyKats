package com.olayg.onlykats.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.olayg.onlykats.R
import com.olayg.onlykats.adapter.BreedAdapter
import com.olayg.onlykats.adapter.KatAdapter
import com.olayg.onlykats.databinding.FragmentBrowseBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.util.ApiState
import com.olayg.onlykats.util.EndPoint
import com.olayg.onlykats.util.PageAction
import com.olayg.onlykats.viewmodel.KatViewModel

/**
 * A simple [Fragment] subclass.
 */
// TODO: 9/11/21 Navigate automatically to SettingsFragment if no data present
// TODO: 9/11/21 Observe breeds and react to states
// TODO: 9/11/21 Show an AlertDialog with error message to prompt user of failures
class BrowseFragment : Fragment(R.layout.fragment_browse) {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!
    private val katViewModel by activityViewModels<KatViewModel>()
    private val katAdapter by lazy { KatAdapter() }
    private val breedAdapter by lazy { BreedAdapter {
        findNavController().navigate(BrowseFragmentDirections.actionBrowseFragmentToDetailsFragment(it))
    } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentBrowseBinding.inflate(layoutInflater, container, false).also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // with(receiver) is 1 of 5 scope functions
    private fun initViews() = with(binding) {
        if (katViewModel.queries == null) {
            findNavController().navigate(BrowseFragmentDirections.actionBrowseFragmentToSettingsFragment())
        }
        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(-1) && dy < 0) {
                    Log.d(TAG, "TOP OF LIST")
                } else if (!recyclerView.canScrollVertically(1) && dy > 0) {
                    Log.d(TAG, "BOTTOM OF LIST")
                    katViewModel.fetchData(PageAction.NEXT)
                }
            }
        })
    }

    private fun setupObservers() = with(katViewModel) {
        if (queries?.endPoint == EndPoint.IMAGES) {
            katState.observe(viewLifecycleOwner) { state ->
                binding.pbLoading.isVisible = state is ApiState.Loading
                if (state is ApiState.Success) loadRandomKats(state.data)
                if (state is ApiState.Failure) handleFailure(state.errorMsg)
            }
        } else {
            breedState.observe(viewLifecycleOwner) { state ->
                binding.pbLoading.isVisible = state is ApiState.Loading
                if (state is ApiState.Success) loadBreeds(state.data)
                if (state is ApiState.Failure) handleFailure(state.errorMsg)
            }
        }
    }

    private fun loadRandomKats(kats: List<Kat>) = with(binding.rvList) {
        Log.d(TAG, "ApiState.Success: $kats")
        if (adapter == null) adapter = katAdapter
        if (katViewModel.currentPageAction == PageAction.FIRST) breedAdapter.clearBreedList()
        katAdapter.updateList(kats)
    }

    private fun loadBreeds(breeds: List<Breed>) = with(binding.rvList) {
        Log.d(TAG, "ApiState.Success: $breeds")
        if (adapter == null) adapter = breedAdapter
        if (katViewModel.currentPageAction == PageAction.FIRST) katAdapter.clearList()
        breedAdapter.updateBreedList(breeds)
    }

    private fun handleFailure(errorMsg: String) {
        Log.d(TAG, "ApiState.Failure: $errorMsg")
    }

    companion object {
        private const val TAG = "BrowseFragment"
    }
}