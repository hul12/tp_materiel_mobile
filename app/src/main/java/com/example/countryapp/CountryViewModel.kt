// CountryViewModel.kt
package com.example.countryapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CountryViewModel(application: Application) : AndroidViewModel(application) {
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> get() = _countries

    init {
        viewModelScope.launch {
            _countries.value = RetrofitInstance.api.getAllCountries()
        }
    }

    fun searchCountries(query: String) {
        _countries.value = _countries.value?.filter {
            it.name.contains(query, ignoreCase = true) || it.capital.contains(query, ignoreCase = true)
        }
    }
}
