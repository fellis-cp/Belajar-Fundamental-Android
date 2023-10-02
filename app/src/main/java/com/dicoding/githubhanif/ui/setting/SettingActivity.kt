package com.dicoding.githubhanif.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.dicoding.githubhanif.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private val viewModel by viewModels<SettingViewModel> {
        SettingViewModel.Factory(SettingPreferences(this))
    }
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupThemeSwitch()

        binding.switchTG.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveTema(isChecked)
        }
    }

    private fun setupThemeSwitch() {
        viewModel.getTema().observe(this) { isDarkMode ->
            val modeText = if (isDarkMode) "Mode Gelap" else "Mode Terang"
            binding.switchTG.text = modeText
            binding.switchTG.isChecked = isDarkMode

            val nightMode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }
}