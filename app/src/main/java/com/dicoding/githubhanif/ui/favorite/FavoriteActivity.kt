package com.dicoding.githubhanif.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubhanif.R
import com.dicoding.githubhanif.databinding.ActivityFavoriteBinding
import com.dicoding.githubhanif.local.DatabaseModul
import com.dicoding.githubhanif.ui.detail.DetailActivity
import com.dicoding.githubhanif.ui.main.UserAdapter

class FavoriteActivity : AppCompatActivity() {
    private val viewModel by viewModels<FavViewModel>{
        FavViewModel.Factory(DatabaseModul(this))
    }
    private lateinit var binding : ActivityFavoriteBinding
    private val adapter by lazy {
        UserAdapter {user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra("item", user)
                startActivity(this)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.rvFav.layoutManager = LinearLayoutManager(this)
        binding.rvFav.adapter = adapter


        viewModel.getUserFav().observe(this){
            adapter.setData(it)
        }

    }
}