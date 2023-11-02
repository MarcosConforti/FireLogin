package com.marcosconforti.firelogin.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.marcosconforti.firelogin.databinding.ActivityMainBinding
import com.marcosconforti.firelogin.ui.detail.DetailActivity
import com.marcosconforti.firelogin.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint


//La utilizaremos como splash
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val splashViewModel:SplashViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       when(splashViewModel.checkDestination()){
           SplashDestination.Home -> navigateToHome()
           SplashDestination.Login -> navigateToLogin()
       }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
    }
    private fun navigateToHome() {
        startActivity(Intent(this,DetailActivity::class.java))
    }
}