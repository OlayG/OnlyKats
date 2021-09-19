package com.olayg.onlykats.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.olayg.onlykats.R
import com.olayg.onlykats.databinding.FragmentUpdateBinding
import com.olayg.onlykats.model.room.KatData
import com.olayg.onlykats.viewmodel.KatDataViewModel

class UpdateFragment : Fragment(R.layout.fragment_update) {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    private val args: UpdateFragmentArgs by navArgs()
    private val katDataViewModel by activityViewModels<KatDataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUpdateBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        getKatData()
        updateKatDetails()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar_menu, menu)
        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title = "Update ${args.currentKat.name}"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) deleteKat()
        if (item.itemId == R.id.back) findNavController().navigateUp()
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        updateKatDetails()
    }

    private fun getKatData() = with(binding) {
        name.setText(args.currentKat.name)
        age.setText(args.currentKat.age.toString())
    }

    private fun updateKatDetails() = with(binding) {
        val name = name.text
        val age = age.text

        updateButton.setOnClickListener {
            if (inputCheck(name.toString(), age)) {
                val updatedKat =
                    KatData(args.currentKat.id, name.toString(), Integer.parseInt(age.toString()))
                Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_updateFragment_to_katDataBrowseFragment)
                katDataViewModel.updateKatData(updatedKat)
            }
            if (!inputCheck(name.toString(), age)) {
                Toast.makeText(requireContext(), "Invalid Input!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun inputCheck(name: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(name) && age.isEmpty())
    }

    private fun deleteKat() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("yes") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Successfully removed ${args.currentKat.name}",
                Toast.LENGTH_LONG
            ).show()
            katDataViewModel.deleteKatData(args.currentKat)
            findNavController().navigate(R.id.action_updateFragment_to_katDataBrowseFragment)
        }
        builder.setNegativeButton("no") { _, _ -> }
        builder.setTitle("Delete ${args.currentKat.name}?")
        builder.setMessage("Are you sure you want to delete ${args.currentKat.name}")
        builder.create().show()
    }
}