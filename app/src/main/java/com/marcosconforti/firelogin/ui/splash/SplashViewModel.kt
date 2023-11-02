package com.marcosconforti.firelogin.ui.splash

import androidx.lifecycle.ViewModel
import com.marcosconforti.firelogin.data.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authService: AuthService) : ViewModel() {

    private fun isUserLogged(): Boolean {
        return authService.isUserLogged()
    }

    fun checkDestination():SplashDestination {
        val isUserLogged = isUserLogged()
        return if (isUserLogged) {
            SplashDestination.Home
        } else {
            SplashDestination.Login
        }
    }
}