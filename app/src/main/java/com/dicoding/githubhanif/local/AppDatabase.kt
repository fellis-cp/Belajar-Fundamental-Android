package com.dicoding.githubhanif.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.githubhanif.api.model.ResponseUserGithub

@Database(entities = [ResponseUserGithub.Item::class], version = 1 , exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
}