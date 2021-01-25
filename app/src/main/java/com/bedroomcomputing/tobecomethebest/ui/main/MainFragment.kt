package com.bedroomcomputing.tobecomethebest.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bedroomcomputing.tobecomethebest.MainActivity
import com.bedroomcomputing.tobecomethebest.R
import com.bedroomcomputing.tobecomethebest.databinding.MainFragmentBinding
import com.bedroomcomputing.tobecomethebest.db.AppDatabase
import com.bedroomcomputing.tobecomethebest.db.SettingsDao

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = MainFragmentBinding.inflate(inflater)
        val settingsDao = AppDatabase.getDatabase(requireContext()).settingsDao()
        viewModel = MainViewModelFactory(settingsDao).create(MainViewModel::class.java)

        viewModel.settings.observe(viewLifecycleOwner, Observer {
            it?.let{
                MainActivity.settings = it
            }
        })

        binding.buttonStart.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToGameFragment()
            findNavController().navigate(action)
        }

        binding.buttonSettings.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }


}