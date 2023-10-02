package com.dicoding.githubhanif.ui.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.githubhanif.R
import com.dicoding.githubhanif.api.model.ResponseDetailUser
import com.dicoding.githubhanif.api.model.ResponseUserGithub
import com.dicoding.githubhanif.databinding.ActivityDetailBinding
import com.dicoding.githubhanif.local.DatabaseModul
import com.dicoding.githubhanif.ui.follow.FollowFragment
import com.dicoding.githubhanif.ui.follow.SectionPagerAdapter
import com.dicoding.githubhanif.ui.main.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory(DatabaseModul(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getParcelableExtra<ResponseUserGithub.Item>("item")
        val username = item?.login ?: ""

        setupViews(item)
        observeDatabaseChanges(username, item?.id ?: 0)
        setupTabLayout(username)
        setupFavoriteButton(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViews(userItem: ResponseUserGithub.Item?) {
        userItem?.let { item ->
            val username = item.login
            viewModel.getDetailUser(username)
            viewModel.getFollowers(username)

            viewModel.userDetailResult.observe(this) {
                when (it) {
                    is Result.isSuccess<*> -> {
                        val user = it.data as ResponseDetailUser
                        binding.image.load(user.avatar_url) {
                            transformations(CircleCropTransformation())
                        }

                        binding.nama.text = user.login
                        binding.follower.text = "Follower: ${user.followers}"
                        binding.following.text = "Following: ${user.following}"
                        binding.username.text = user.name
                    }
                    is Result.isError -> {
                        Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                    is Result.isLoad -> {
                        binding.progressBar.isVisible = it.isLoading
                    }
                }
            }
        }
    }

    private fun observeDatabaseChanges(username: String, userId: Int) {
        viewModel.isUserInDatabase.observe(this) { isUserInDb ->
            val colorRes = if (isUserInDb) R.color.red else R.color.white
            binding.btnFavorite.changeIconColor(colorRes)
        }

        viewModel.findFav(userId) {
            binding.btnFavorite.changeIconColor(R.color.white)
        }

        viewModel.favSucces.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.red)
        }

        viewModel.favDel.observe(this) {
            binding.btnFavorite.changeIconColor(R.color.white)
        }

        viewModel.checkUserInDatabase(userId)
    }

    private fun setupTabLayout(username: String) {
        val fragments = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.FOLLOWERS),
            FollowFragment.newInstance(FollowFragment.FOLLOWING)
        )

        val titleFragments = mutableListOf(
            getString(R.string.Follower),
            getString(R.string.Following),
        )

        val adapter = SectionPagerAdapter(this, fragments)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tab, binding.viewpager) { tab, position ->
            tab.text = titleFragments[position]
        }.attach()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupFavoriteButton(userItem: ResponseUserGithub.Item?) {
        binding.btnFavorite.setOnClickListener {
            viewModel.setFav(userItem)
        }
    }
}

fun FloatingActionButton.changeIconColor(colorRes: Int) {
    imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this.context, colorRes))
}