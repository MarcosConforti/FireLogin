package com.marcosconforti.firelogin.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.marcosconforti.firelogin.R
import com.marcosconforti.firelogin.databinding.ActivitySignUpBinding
import com.marcosconforti.firelogin.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect {
                    binding.progressBar.isVisible = it
                }
            }
        }
    }

    private fun initListeners() {
        binding.btnSignIn.setOnClickListener {
           viewModel.register(
               user = binding.tietUser.text.toString(),
               password = binding.tietPassword.text.toString()
           ){navigateToDetail()}
        }
    }

    private fun navigateToDetail() {
        startActivity(Intent(this,DetailActivity::class.java))
    }
}