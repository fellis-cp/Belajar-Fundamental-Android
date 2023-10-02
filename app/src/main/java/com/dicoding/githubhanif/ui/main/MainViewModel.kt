package com.dicoding.githubhanif.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.githubhanif.api.retrofit.ApiClient
import com.dicoding.githubhanif.ui.setting.SettingPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val preferences : SettingPreferences) : ViewModel() {

    fun geTheme() = preferences.getTema().asLiveData()
 val userResult = MutableLiveData<Result>()

    fun getUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .searchUserGithub(mapOf(
                        "q" to username
                    ))

                emit(response)
            }.onStart {
                userResult.value = Result.isLoad(true)
            }.onCompletion {
                userResult.value =Result.isLoad(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                userResult.value = Result.isError(it)
            }.collect {
               userResult.value = Result.isSuccess(it.items)
            }
        }
    }

    class Factory(private val preferences: SettingPreferences):
            ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
            }

}
