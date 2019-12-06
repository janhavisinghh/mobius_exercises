package com.example.counterapplication.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginModel(
    val loginStatus : LoginStatus?,
    val usernameInputError: ValidationError?,
    val passwordInputError: ValidationError?,
    val networkErrorMessage: NetworkErrorMessage?
) : Parcelable {
    companion object {
        val INIT = LoginModel(null, null, null, null)
    }

    fun loggingIn(): LoginModel {
        return copy(loginStatus = LoginStatus.IN_PROGRESS)
    }

    fun inputError( usernameInputError: ValidationError?, passwordInputError: ValidationError?): LoginModel {
        return copy(usernameInputError = usernameInputError, passwordInputError = passwordInputError)
    }

    fun loginFail(): LoginModel {
        return copy(loginStatus = LoginStatus.FAIL)
    }

    fun clearUsernameError(): LoginModel {
        return copy(usernameInputError = null)
    }

    fun clearPasswordError(): LoginModel {
        return copy(passwordInputError = null)
    }
}
