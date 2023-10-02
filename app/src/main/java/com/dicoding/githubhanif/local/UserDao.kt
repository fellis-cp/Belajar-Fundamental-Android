package com.dicoding.githubhanif.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.githubhanif.api.model.ResponseUserGithub

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user : ResponseUserGithub.Item)

    @Query("SELECT * FROM User")
    fun loadS(): LiveData<MutableList<ResponseUserGithub.Item>>

    @Query("SELECT * FROM User WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): ResponseUserGithub.Item

    @Delete
    fun del(user: ResponseUserGithub.Item)



}