package com.dicoding.githubhanif.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.githubhanif.R
import com.dicoding.githubhanif.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }
    private lateinit var binding  : ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTema().observe(this) {
            if (it) {
                binding.switchTG.text = "Mode Gelap"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                binding.switchTG.text = "Mode Terang"
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            binding.switchTG.isChecked = it
        }

        binding.switchTG.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTema(isChecked)
        }
    }


}