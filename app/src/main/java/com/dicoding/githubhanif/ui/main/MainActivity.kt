package com.dicoding.githubhanif.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubhanif.R
import com.dicoding.githubhanif.api.model.ResponseUserGithub
import com.dicoding.githubhanif.databinding.ActivityMainBinding
import com.dicoding.githubhanif.ui.detail.DetailActivity
import com.dicoding.githubhanif.ui.favorite.FavoriteActivity
import com.dicoding.githubhanif.ui.setting.SettingActivity
import com.dicoding.githubhanif.ui.setting.SettingPreferences

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { UserAdapter{user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
    }  }
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>{
        MainViewModel.Factory(SettingPreferences(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showRv()

        viewModel.geTheme().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }



        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return  false
            }


        })



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

        viewModel.getUser("hanif")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }

            R.id.setting -> {
                Intent(this , SettingActivity::class.java).apply {
                    startActivity(this)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun showRv () {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }



}