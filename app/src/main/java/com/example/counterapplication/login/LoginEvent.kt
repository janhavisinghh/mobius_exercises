package com.example.counterapplication.login

sealed class LoginEvent

data class LoginButtonClicked(val username: String, val password: String) : LoginEvent()

data class InputIsValid(val username: String, val password: String) : LoginEvent()

data class InputIsInvalid(val usernameError: ValidationError?, val passwordError: ValidationError?) : LoginEvent()

object UserNameEntered : LoginEvent()

object PasswordEntered: LoginEvent()

data class NetworkCallFailed(val errorMessage: NetworkErrorMessage?) : LoginEvent()

data class NetworkCallSuccess(val token: String) : LoginEvent()