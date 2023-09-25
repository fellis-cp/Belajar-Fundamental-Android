package com.dicoding.githubhanif.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubhanif.api.retrofit.ApiClient
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

 val userResult = MutableLiveData<Result>()

    fun getUser() {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getUser()

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
               userResult.value = Result.isSuccess(it)
            }
        }
    }
}
