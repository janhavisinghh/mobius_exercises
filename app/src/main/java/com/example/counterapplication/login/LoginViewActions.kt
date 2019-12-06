package com.example.counterapplication.login

interface LoginViewActions {

    fun showErrorMessageToast(error: NetworkErrorMessage)
    fun clearUsernameError()
    fun clearPasswordError()
    fun showHomeScreen()
}
