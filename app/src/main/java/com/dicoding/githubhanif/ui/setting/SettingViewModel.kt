package com.dicoding.githubhanif.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(private val pf : SettingPreferences) : ViewModel() {


    class Factory(private val pf: SettingPreferences):ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingViewModel(pf) as T
    }

    fun getTema() = pf.getTema().asLiveData()

    fun saveTema(isModeMalam: Boolean){
        viewModelScope.launch {
            pf.saveTema(isModeMalam)
        }
    }



}