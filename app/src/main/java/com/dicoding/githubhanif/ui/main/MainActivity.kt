package com.dicoding.githubhanif.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubhanif.api.model.ResponseUserGithub
import com.dicoding.githubhanif.databinding.ActivityMainBinding
import com.dicoding.githubhanif.ui.detail.DetailActivity

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { UserAdapter{
            Intent(this, DetailActivity::class.java).apply {
                putExtra("username", it.login)
                startActivity(this)
            }
    }  }
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRv()

        viewModel.getUser()

        viewModel.userResult.observe(this){
           when(it){
               is Result.isSuccess<*> -> {
                adapter.setData(it.data as MutableList<ResponseUserGithub.Item>)
               }
               is Result.isError -> {
                   Toast.makeText(this , it.exception.message.toString() , Toast.LENGTH_SHORT).show()
               }
               is Result.isLoad ->{
                   binding.progressBar.isVisible = it.isLoading
               }
           }

        }


    }







    private fun showRv () {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }



}