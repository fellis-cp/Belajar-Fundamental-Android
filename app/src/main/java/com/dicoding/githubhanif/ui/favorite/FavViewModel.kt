package com.dicoding.githubhanif.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubhanif.local.DatabaseModul
import com.dicoding.githubhanif.ui.detail.DetailViewModel

class FavViewModel(private val databaseModul: DatabaseModul) : ViewModel() {

    class Factory(private val db: DatabaseModul) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavViewModel(db) as T


    }

    fun getUserFav() = databaseModul.userDao.loadS()



}