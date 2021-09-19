package com.olayg.onlykats.view

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

import com.olayg.onlykats.R
import com.olayg.onlykats.model.room.KatData
import com.olayg.onlykats.viewmodel.KatDataViewModel
import com.olayg.onlykats.databinding.FragmentAddBinding

class AddFragment : Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private val katDataViewModel by activityViewModels<KatDataViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentAddBinding.inflate(layoutInflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ACTION BAR
        setHasOptionsMenu(true)
        // CLASS METHODS
        handleAddKat()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actionbar_menu, menu)
        (activity as AppCompatActivity).supportActionBar?.title = "Add a Kat"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.back) {
            findNavController().navigateUp()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleAddKat() {
        binding.addButton.setOnClickListener {
            insertData()
        }
    }

    private fun insertData() = with(katDataViewModel) {
        val name = binding.name.text.toString()
        val age = binding.age.text

        if (inputCheck(name, age)) {
            val kat = KatData(0, name, Integer.parseInt(age.toString()))
            katDataViewModel.addKatData(kat)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_addKatFragment_to_katDataBrowseFragment)
        }
        if (!inputCheck(name, age)) {
            Toast.makeText(requireContext(), "Invalid Input!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(name: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(name) && age.isEmpty())
    }
}
