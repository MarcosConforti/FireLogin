package com.marcosconforti.firelogin.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle.State.*
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.marcosconforti.firelogin.R
import com.marcosconforti.firelogin.databinding.ActivityLoginBinding
import com.marcosconforti.firelogin.ui.detail.DetailActivity
import com.marcosconforti.firelogin.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(STARTED) {
                viewModel.isLoading.collect {
                    binding.progressBar.isVisible = it
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnLogin.setOnClickListener {
            viewModel.login(
                user = binding.tietUser.text.toString(),
                password = binding.tietPassword.text.toString()
            ) { navigateToDetail() }
        }
        binding.signUp.setOnClickListener { navigateToSingUp() }
    }

    private fun navigateToSingUp() {
        startActivity(Intent(this,SignUpActivity::class.java))
    }

    private fun navigateToDetail() {
        startActivity(Intent(this, DetailActivity::class.java))
    }
}