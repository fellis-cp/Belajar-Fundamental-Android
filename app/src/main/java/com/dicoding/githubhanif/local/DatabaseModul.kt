package com.dicoding.githubhanif.local

import android.content.Context
import androidx.room.Room

class DatabaseModul(private val context: Context) {
    private val database = Room.databaseBuilder(context, AppDatabase::class.java, "githubuser.db")
        .allowMainThreadQueries()
        .build()


    val userDao = database.userDao()
}