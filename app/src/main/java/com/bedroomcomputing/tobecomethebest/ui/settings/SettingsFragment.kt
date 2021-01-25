package com.bedroomcomputing.tobecomethebest.ui.settings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bedroomcomputing.tobecomethebest.MainActivity
import com.bedroomcomputing.tobecomethebest.R
import com.bedroomcomputing.tobecomethebest.databinding.SettingsFragmentBinding
import com.bedroomcomputing.tobecomethebest.db.AppDatabase

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val settingsDao = AppDatabase.getDatabase(requireContext()).settingsDao()
        viewModel = SettingsViewModelFactory(settingsDao).create(SettingsViewModel::class.java)


        binding = SettingsFragmentBinding.inflate(inflater)

        val settings = MainActivity.settings
        viewModel.settings = settings

        val listener = CompoundButton.OnCheckedChangeListener{ compoundButton: CompoundButton, checked: Boolean ->
            viewModel.settings.autoPlay = checked
        }
        binding.switchAuto.setOnCheckedChangeListener(listener)

        binding.editTextSettingsName.setText(settings.userName)
        binding.editTextSettingsTitle.setText(settings.rankingTitle)
        binding.seekBarOutstanding.progress = settings.outstandingRate
        binding.switchAuto.isChecked = settings.autoPlay

        binding.button.setOnClickListener{
            // asssign values to settings
            viewModel.settings.userName = binding.editTextSettingsName.text.toString()
            viewModel.settings.rankingTitle = binding.editTextSettingsTitle.text.toString()
            viewModel.settings.outstandingRate = binding.seekBarOutstanding.progress
            Log.i("Setttings", "${viewModel.settings.userName}")
            viewModel.save()
            val action = SettingsFragmentDirections.actionSettingsFragmentToMainFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}