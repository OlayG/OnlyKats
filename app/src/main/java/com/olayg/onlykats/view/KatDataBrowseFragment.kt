package com.olayg.onlykats.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.olayg.onlykats.R
import com.olayg.onlykats.adapter.KatDataAdapter
import com.olayg.onlykats.viewmodel.KatDataViewModel
import com.olayg.onlykats.databinding.FragmentKatDataBinding
import com.olayg.onlykats.model.room.KatData

class KatDataBrowseFragment : Fragment(R.layout.fragment_kat_data) {

    private var _binding: FragmentKatDataBinding? = null
    private val binding get() = _binding!!
    private val katDataViewModel by activityViewModels<KatDataViewModel>()
    private val katDataAdapter by lazy { KatDataAdapter(listener = ::updateKatDetails) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentKatDataBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ACTION BAR
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title = "Your Kat List"
        setHasOptionsMenu(true)

        // CLASS METHODS
        handleAddButton()
        setUpObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            deleteAllKats()
        }
        if (item.itemId == R.id.back) {
            findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setUpObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpObservers() = with(binding.rvKatDataList) {
        this.adapter = katDataAdapter
        this.layoutManager = LinearLayoutManager(requireContext())

        katDataViewModel.katDataState.observe(viewLifecycleOwner) { kat ->
            if (kat.isNotEmpty()) katDataAdapter.setData(kat)
        }
    }

    private fun handleAddButton() {
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_katDataBrowseFragment_to_addKatFragment)
        }
    }

    private fun updateKatDetails(katData: KatData) {
        findNavController().navigate(
            KatDataBrowseFragmentDirections.actionKatDataBrowseFragmentToUpdateFragment(katData)
        )
    }

    private fun deleteAllKats() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("yes") { _, _ ->
            katDataViewModel.deleteAllKats()
            Toast.makeText(requireContext(), "Successfully deleted every kat!", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("no") { _, _ -> }
        builder.setTitle("All kats staged for deletion.")
        builder.setMessage("Are you sure you want to delete every kat?")
        builder.create().show()
    }
}
