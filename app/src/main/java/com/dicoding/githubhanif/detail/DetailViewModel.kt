package com.dicoding.githubhanif.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubhanif.api.retrofit.ApiClient
import com.dicoding.githubhanif.ui.main.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    val userDetailResult = MutableLiveData<Result>()

    fun getDetailUser(username: String) {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getDetailUser(username)

                emit(response)
            }.onStart {
                userDetailResult.value = Result.isLoad(true)
            }.onCompletion {
                userDetailResult.value = Result.isLoad(false)
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                userDetailResult.value = Result.isError(it)
            }.collect {
                userDetailResult.value = Result.isSuccess(it)
            }
        }
    }
}



