package com.marcosconforti.firelogin.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcosconforti.firelogin.data.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val authService: AuthService):ViewModel() {
    fun signOut(navigateToLogin:()-> Unit) {
       viewModelScope.launch(Dispatchers.IO) {
           authService.logout()
       }
        navigateToLogin()
    }
}