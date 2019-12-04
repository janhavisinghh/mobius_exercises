package com.example.counterapplication.login

sealed class LoginEffect

data class CheckIfValidInput(val username: String, val password: String) : LoginEffect()

data class LoginApi(val username: String, val password: String) : LoginEffect()

object ClearUsernameError : LoginEffect()

object ClearPasswordError : LoginEffect()

data class ShowErrorMessageToast(val errorMessage: NetworkErrorMessage) : LoginEffect()

object ShowHomeScreen : LoginEffect()

data class SaveToken(val token: String) : LoginEffect()