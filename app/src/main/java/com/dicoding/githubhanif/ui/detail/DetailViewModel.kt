package com.dicoding.githubhanif.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.dicoding.githubhanif.api.model.ResponseUserGithub
import com.dicoding.githubhanif.api.retrofit.ApiClient
import com.dicoding.githubhanif.local.DatabaseModul
import com.dicoding.githubhanif.ui.main.Result
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(private val db: DatabaseModul) : ViewModel() {
    val isUserInDatabase: LiveData<Boolean> = MutableLiveData()
    val favDel = MutableLiveData<Boolean>()
    val favSucces = MutableLiveData<Boolean>()
    val userDetailResult = MutableLiveData<Result>()
    val userFollowersResult = MutableLiveData<Result>()
    val userFollowingResult = MutableLiveData<Result>()

    private var isFavorite = false

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


    fun getFollowing(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getFollowing(username)

                emit(response)
            }.onStart {
                userFollowingResult.value = Result.isLoad(true)
            }.onCompletion {
                userFollowingResult.value = Result.isLoad(false)
            }.catch {
                it.printStackTrace()
                userFollowingResult.value = Result.isError(it)
            }.collect {
                userFollowingResult.value = Result.isSuccess(it)
            }
        }



    }

    fun getFollowers(username: String){
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getFollowers(username)

                emit(response)
            }.onStart {
                userFollowersResult.value = Result.isLoad(true)
            }.onCompletion {
                userFollowersResult.value = Result.isLoad(false)
            }.catch {
                it.printStackTrace()
                userFollowersResult.value = Result.isError(it)
            }.collect {
                userFollowersResult.value = Result.isSuccess(it)
            }
        }



    }

    fun checkUserInDatabase(userId: Int) {
        viewModelScope.launch {
            val count = db.userDao.userExists(userId)
            (isUserInDatabase as MutableLiveData).postValue(count > 0)
        }
    }

    fun setFav(item: ResponseUserGithub.Item?){
        viewModelScope.launch {
            item?.let {
                if(isFavorite){
                    favDel.value = false
                    db.userDao.del(item)
                }else{
                    favSucces.value = true
                    db.userDao.insert(item)
                }
            }
            isFavorite = !isFavorite

        }
    }

    fun findFav(id:Int, listenFavo: () -> Unit){
        viewModelScope.launch{
           val favuser =  db.userDao.findById(id)
            if (favuser == null ){
                listenFavo()
                isFavorite = true
            }
        }
    }


    class Factory(private val db: DatabaseModul) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T

    }

}



