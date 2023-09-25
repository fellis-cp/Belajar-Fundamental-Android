package com.dicoding.githubhanif.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.githubhanif.api.model.ResponseDetailUser
import com.dicoding.githubhanif.databinding.ActivityDetailBinding
import com.dicoding.githubhanif.ui.main.Result

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding
    private val viewmodel by viewModels<DetailViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username = intent.getStringExtra("username")?:""

        viewmodel.getDetailUser(username)

        viewmodel.userDetailResult.observe(this){
            when(it){
                is Result.isSuccess<*> -> {
                    val user = it.data as ResponseDetailUser
                    binding.image.load(user.avatar_url){
                        transformations(CircleCropTransformation())
                    }

                    binding.nama.text = user.name
                    binding.follower.text = "Follower: ${user.followers}"
                    binding.following.text = "Following: ${user.following}"
                    binding.repository.text = "Repository: ${user.public_repos}"

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
}