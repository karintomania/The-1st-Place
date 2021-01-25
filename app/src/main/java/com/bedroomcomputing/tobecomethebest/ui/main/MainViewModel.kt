package com.bedroomcomputing.tobecomethebest.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bedroomcomputing.tobecomethebest.db.SettingsDao
import com.bedroomcomputing.tobecomethebest.ui.settings.SettingsViewModel
import kotlinx.coroutines.launch

class MainViewModel(val settingsDao: SettingsDao) : ViewModel() {
    val settings = settingsDao.getSettings()

    init{
    }
}

class MainViewModelFactory(val settingsDao: SettingsDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(settingsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

