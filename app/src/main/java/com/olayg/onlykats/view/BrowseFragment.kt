package com.olayg.onlykats.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

import com.olayg.onlykats.R
import com.olayg.onlykats.adapter.BreedAdapter
import com.olayg.onlykats.adapter.KatAdapter
import com.olayg.onlykats.databinding.FragmentBrowseBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.util.*
import com.olayg.onlykats.viewmodel.KatViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class BrowseFragment : Fragment(R.layout.fragment_browse) {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!
    private val katViewModel by activityViewModels<KatViewModel>()
    private val katAdapter by lazy { KatAdapter() }
    private val breedAdapter by lazy { BreedAdapter(listener = ::getKatDetails) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentBrowseBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleDataStore()
        setupObservers()
        handleRecyclerView()
    }

    private fun handleRecyclerView() = with(binding) {
        // Leverages the ViewHolder Pattern to optimizing scrolling and memory consumption
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

    private fun setupObservers() = with(katViewModel, {
        when (queries?.endPoint) {
            // If images endpoint is selected
            EndPoint.IMAGES ->
                katState.observe(viewLifecycleOwner) { state ->
                    // Make the CircularProgressIndicator visible for the user
                    binding.pbLoading.isVisible = state is ApiState.Loading
                    // If we have a success, load up the random kat images, if not, throw error message
                    if (state is ApiState.Success) loadRandomKats(state.data)
                    if (state is ApiState.Failure) handleFailure(state.errorMsg)
                }
            // If the breeds endpoint is selected
            else ->
                // Same logic as above!
                breedState.observe(viewLifecycleOwner) { state ->
                    binding.pbLoading.isVisible = state is ApiState.Loading
                    if (state is ApiState.Success) loadBreeds(state.data)
                    if (state is ApiState.Failure) handleFailure(state.errorMsg)
                }
        }
    })

    private fun handleDataStore() {
        // If there are no view model queries, launch the preferences
        if (katViewModel.queries == null) viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            // Execute the code below only if the view is NOT NULL
            view?.let {
                // Collect the queries and preferences from the PreferenceManager
                PreferenceManager.getInstance(it.context).queries.collect { queries ->
                    // If there are no queries or preferences, automatically navigate to the settings fragment
                    if (queries == null) {
                        findNavController().navigate(BrowseFragmentDirections.actionBrowseFragmentToSettingsFragment())
                        // Otherwise fetch the data and display it in the browse fragment when created
                    } else katViewModel.fetchData(queries)
                }
            }
        }
    }

    private fun loadRandomKats(kats: List<Kat>) = with(binding.rvList) {
        Log.d(TAG, "ApiState.Success: $kats")
        if (adapter == null) adapter = katAdapter
        // If the user wants new list of random kat images, clear the old list of kats
        if (katViewModel.currentPageAction == PageAction.FIRST) katAdapter.clearList()
        // Clear the list of breeds if any are present
        breedAdapter.clearList()
        // Lastly update the kat list with the newly selected kats
        katAdapter.updateList(kats)
    }

    private fun loadBreeds(breeds: List<Breed>) = with(binding.rvList) {
        Log.d(TAG, "ApiState.Success: $breeds")
        if (adapter == null) adapter = breedAdapter
        // if the user wants a new list of breeds, clear the old lost of breeds
        if (katViewModel.currentPageAction == PageAction.FIRST) breedAdapter.clearList()
        // Clear the list of random kat images if any are present
        katAdapter.clearList()
        // Lastly, update the breed list with the newly selected breeds
        breedAdapter.updateBreedList(breeds)
    }

    private fun getKatDetails(breed: Breed) {
        // Upon pressing a kat image, navigate to the details fragment along with the details of the kat.
        findNavController().navigate(
            BrowseFragmentDirections.actionBrowseFragmentToDetailsFragment(breed)
        )
    }

    private fun handleFailure(errorMsg: String) {
        Log.d(TAG, "ApiState.Failure: $errorMsg")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "BrowseFragment"
    }
}