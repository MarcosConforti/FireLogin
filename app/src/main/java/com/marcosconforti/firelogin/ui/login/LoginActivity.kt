package com.marcosconforti.firelogin.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.marcosconforti.firelogin.databinding.ActivityLoginBinding
import com.marcosconforti.firelogin.ui.detail.DetailActivity
import com.marcosconforti.firelogin.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    //generalmene, los launchers van arriba
    private val googleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    viewModel.loginWithGoogle(account.idToken!!) { navigateToDetail() }
                } catch (e: ApiException) {
                    Toast.makeText(this, "Ha ocurrido un error ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }

        }

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

        binding.btnLoginWithGoogle.setOnClickListener {
            viewModel.onGoogleLoginSelected {
                googleLauncher.launch(it.signInIntent)
            }
        }
    }

    private fun navigateToSingUp() {
        startActivity(Intent(this, SignUpActivity::class.java))
    }

    private fun navigateToDetail() {
        startActivity(Intent(this, DetailActivity::class.java))
    }

}