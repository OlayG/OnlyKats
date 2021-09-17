package com.olayg.onlykats.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.olayg.onlykats.adapter.BreedAdapter
import com.olayg.onlykats.adapter.KatAdapter
import com.olayg.onlykats.databinding.FragmentBrowseBinding
import com.olayg.onlykats.model.Breed
import com.olayg.onlykats.model.Kat
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.util.ApiState
import com.olayg.onlykats.util.EndPoint
import com.olayg.onlykats.util.PageAction
import com.olayg.onlykats.util.PreferencesKey
import com.olayg.onlykats.viewmodel.KatViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

/**
 * A simple [Fragment] subclass.
 */
// TODO: 9/11/21 Navigate automatically to SettingsFragment if no data present
// TODO: 9/11/21 Observe breeds and react to states
// TODO: 9/11/21 Show an AlertDialog with error message to prompt user of failures
class BrowseFragment : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!
    private val katViewModel by activityViewModels<KatViewModel>()
    private val katAdapter by lazy { KatAdapter() }
    private val listener: OnItemClickListener? = null

    private val breedAdapter by lazy {
        BreedAdapter() { breed ->
            Log.i(TAG, "clicked: ${breed.wikipediaUrl}")
            var testIt  = 3;
           // findNavController().navigate(BrowseFragmentDirections.actionDetailsFragment(testIt))



            val action = BrowseFragmentDirections.actionDetailsFragment(breed)
             findNavController().navigate(action)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentBrowseBinding.inflate(layoutInflater, container, false).also {
        _binding = it
        initViews()
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if(katViewModel.queries == null ) viewLifecycleOwner.lifecycleScope.launchWhenCreated {
        view.context.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[PreferencesKey.ENDPOINT]?.let { Queries(EndPoint.valueOf(it), 10, 0) }
            }
            .collect {
                if (it == null) {
                    //requires safe args from navigation dependencies for directions
                    findNavController().navigate(BrowseFragmentDirections.actionSettingsFragment())
                }
                else katViewModel.fetchData(it)

            }
    }



        //   breedAdapter = ContentAdapter(breeds) { breed -> Log.i(TAG, "onViewCreated: ")}
        setupObservers()
        initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // with(receiver) is 1 of 5 scope functions
    private fun initViews() = with(binding) {
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
        katState.observe(viewLifecycleOwner) { state ->
            binding.pbLoading.isVisible = state is ApiState.Loading
            if (state is ApiState.Success) loadKats(state.data)
            if (state is ApiState.Failure) handleFailure(state.errorMsg)
        }

        breedState.observe(viewLifecycleOwner) { state ->
            Log.i(TAG, "the state is: ${state} ")
            binding.pbLoading.isVisible = state is ApiState.Loading
            if (state is ApiState.Success) loadBreeds(state.data)
            if (state is ApiState.Failure) handleFailure(state.errorMsg)
        }
    }


    private fun loadKats(kats: List<Kat>) = with(binding.rvList) {
        Log.d(TAG, "ApiState.Success: $kats")
        if (adapter == null || adapter == breedAdapter) adapter = katAdapter
        if (katViewModel.currentPageAction == PageAction.FIRST) katAdapter.clear()
        breedAdapter.clear()
        katAdapter.updateList(kats)
    }

    private fun loadBreeds(breeds: List<Breed>) = with(binding.rvList) {
        Log.d(TAG, "ApiState.Success: $breeds")
        if (adapter == null || adapter == katAdapter) adapter = breedAdapter
        if (katViewModel.currentPageAction == PageAction.FIRST) breedAdapter.clear()
        katAdapter.clear()
        breedAdapter.updateList(breeds)
    }

    private fun handleFailure(errorMsg: String) {
        Log.d(TAG, "ApiState.Failure: $errorMsg")
    }

    companion object {
        private const val TAG = "BrowseFragment"
    }
}