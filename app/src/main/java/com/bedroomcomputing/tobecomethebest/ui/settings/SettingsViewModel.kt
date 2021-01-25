package com.bedroomcomputing.tobecomethebest.ui.settings

import android.util.Log
import androidx.lifecycle.*
import com.bedroomcomputing.tobecomethebest.db.Settings
import com.bedroomcomputing.tobecomethebest.db.SettingsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(val settingsDao: SettingsDao): ViewModel() {
    var settings = Settings()

   fun save(){
       viewModelScope.launch { insert() }
   }

    suspend fun insert(){
        settingsDao.insertSettings(settings)
    }

}

class SettingsViewModelFactory(val settingsDao: SettingsDao) : ViewModelProvider.Factory {
   override fun <T : ViewModel> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
         @Suppress("UNCHECKED_CAST")
         return SettingsViewModel(settingsDao) as T
      }
      throw IllegalArgumentException("Unknown ViewModel class")
   }
}

