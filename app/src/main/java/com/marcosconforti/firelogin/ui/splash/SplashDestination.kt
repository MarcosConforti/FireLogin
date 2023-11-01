package com.marcosconforti.firelogin.ui.splash

sealed class SplashDestination{

    object Login:SplashDestination()
    object Home:SplashDestination()
}
