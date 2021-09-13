package com.olayg.onlykats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textview.MaterialTextView
import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentSettingsBinding
import com.olayg.onlykats.model.request.Queries
import com.olayg.onlykats.util.EndPoint
import com.olayg.onlykats.viewmodel.KatViewModel

/**
 * A simple [Fragment] subclass.
 */
// TODO: 9/11/21 Navigate back on apply click
// TODO: 9/10/21 Use toggle method to show or hide unique views for Images (Try using Group in ConstraintLayout)
// TODO: 9/10/21 Use toggle method to show or hide unique views for Breeds
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val katViewModel by activityViewModels<KatViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSettingsBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservers()
    }

    override fun onResume() {
        super.onResume()
        initEndpointDropdown()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() = with(binding) {
        katViewModel.queries?.let { sliderLimit.value = it.limit.toFloat() }
        sliderLimit.addOnChangeListener { _, _, _ -> toggleApply() }
        btnApply.setOnClickListener { katViewModel.fetchData(getKatQueries()) }
    }

    private fun initObservers() = with(katViewModel) {
        stateUpdated.observe(viewLifecycleOwner) { toggleApply() }
    }

    private fun initEndpointDropdown() = with(binding.etEndpoint) {
        katViewModel.queries?.endPoint?.let {
            setText(it.name)
            setSelection(it.ordinal)
        }
        setAdapter(ArrayAdapter(context, R.layout.item_endpoint, EndPoint.values().map { it.name }))
        setOnItemClickListener { _, view, _, _ ->
            val selectedEndpointText = (view as MaterialTextView).text.toString()
            when (EndPoint.valueOf(selectedEndpointText)) {
                EndPoint.IMAGES -> {
                    toggleImagesView(true)
                    toggleBreedsView(false)
                }
                EndPoint.BREEDS -> {
                    toggleBreedsView(true)
                    toggleImagesView(false)
                }
            }
            toggleApply()
        }
    }

    private fun toggleImagesView(show: Boolean) = with(binding) {}

    private fun toggleBreedsView(show: Boolean) = with(binding) {}

    private fun toggleApply() {
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