package com.olayg.onlykats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.google.android.material.textview.MaterialTextView
import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentSettingsBinding
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.util.EndPoint
import com.olayg.onlykats.util.PreferenceKey
import com.olayg.onlykats.util.PreferenceManager
import com.olayg.onlykats.viewmodel.KatViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val katViewModel by activityViewModels<KatViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(layoutInflater, container, false).also {
        _binding = it

    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleApplyButtonAndSliderLimit()
        settingsObserver()
    }

    override fun onResume() {
        super.onResume()
        initEndpointDropdown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun settingsObserver() = with(katViewModel) {
        // Observe the settings and update it anytime the state changes
        stateUpdated.observe(viewLifecycleOwner) { toggleApply() }
    }

    private fun handleApplyButtonAndSliderLimit() = with(binding) {
        // Since it only accepts floats, change the value of the slider limit to a float
        katViewModel.queries?.let { sliderLimit.value = it.limit.toFloat() }
        sliderLimit.addOnChangeListener { _, _, _ -> toggleApply() }

        btnApply.setOnClickListener {
            val queries = getKatQueries()
            // Launch and run the code within the scope when the LifeCycleCoroutineScope is at least in the RESUMED state
            viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                // Get the context from the Preference Manager the user can edit it accordingly
                PreferenceManager.getInstance(it.context).dataStore.edit { settings ->
                    // Execute the code below only if we have a valid endpoint
                    queries.endPoint?.name?.let {
                        // update the endpoint data in the dataStore
                        settings[PreferenceKey.ENDPOINT] = it
                    }
                    // update the limit data in the datastore
                    settings[PreferenceKey.LIMIT] = queries.limit
                }
            }
            katViewModel.fetchData(queries)
            // Navigate to the browse fragment when the user presses the apply button
            findNavController().navigateUp()
        }
    }

    private fun initEndpointDropdown() = with(binding.etEndpoint) {
        // Only run the code below if the endpoint is not null
        katViewModel.queries?.endPoint?.let {
            setText(it.name)
            setSelection(it.ordinal)
        }
        // Return the context, resource, and textViewResourceId for each object->(KAT) in the collection
        setAdapter(ArrayAdapter(context, R.layout.item_endpoint, EndPoint.values().map { it.name }))
        setOnItemClickListener { _, view, _, _ ->
            // Convert the View text to a string
            val selectedEndpointText = (view as MaterialTextView).text.toString()
            // Define a conditional expression for the selected endpoint
            when (EndPoint.valueOf(selectedEndpointText)) {
                // If the selected endpoint is IMAGES toggle images
                EndPoint.IMAGES -> {
                    toggleImagesView(true)
                    toggleBreedsView(false)
                }
                // If the selected endpoint is BREEDS, toggle the breed
                EndPoint.BREEDS -> {
                    toggleImagesView(false)
                    toggleBreedsView(true)
                }
            }
            // If an endpoint and limit is selected make the apply button visible
            toggleApply()
        }
    }

    private fun toggleImagesView(show: Boolean) = with(binding) {}

    private fun toggleBreedsView(show: Boolean) = with(binding) {}

    private fun toggleApply() {
        // Make the apply button visible if the query has been validated
        binding.btnApply.isVisible = validateQuery()
    }

    private fun validateQuery(): Boolean {
        val newQuery = getKatQueries()
        return katViewModel.queries?.let {
            return@let it.endPoint != newQuery.endPoint || (it.limit != newQuery.limit && newQuery.limit >= 10)
        } ?: (newQuery.endPoint != null && newQuery.limit >= 10)
    }

    private fun getKatQueries() = Queries(
        endPoint = binding.etEndpoint.text.toString().run {
            if (isNotBlank()) EndPoint.valueOf(this) else null
        },
        limit = binding.sliderLimit.value.toInt(),
        page = katViewModel.queries?.page
    )
}