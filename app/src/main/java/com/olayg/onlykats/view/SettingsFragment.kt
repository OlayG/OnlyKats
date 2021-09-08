package com.olayg.onlykats.view

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentSettingsBinding
import com.olayg.onlykats.viewmodel.KatViewModel

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private val katViewModel by activityViewModels<KatViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        initView()
    }

    private fun initView() = with(binding) {
        sliderLimit.value = katViewModel.limit.toFloat()
        sliderLimit.addOnChangeListener { _, value, _ ->
            toggleApply(katViewModel.limit != value.toInt())
        }
        btnApply.setOnClickListener {
            katViewModel.fetchKatList(sliderLimit.value.toInt())
        }
    }

    private fun toggleApply(dataChanged: Boolean) {
        binding.btnApply.isVisible = dataChanged
    }
}