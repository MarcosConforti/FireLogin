package com.marcosconforti.firelogin.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.marcosconforti.firelogin.databinding.ActivityMainBinding
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
    }
}