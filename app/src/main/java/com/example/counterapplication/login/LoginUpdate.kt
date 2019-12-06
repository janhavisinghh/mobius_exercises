package com.example.counterapplication.login

import com.spotify.mobius.Next
import com.spotify.mobius.Next.*
import com.spotify.mobius.Update

class LoginUpdate : Update<LoginModel, LoginEvent, LoginEffect> {
    override fun update(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
        return when (event) {
            is LoginButtonClicked -> {
                dispatch(setOf(CheckIfValidInput(event.username, event.password) as LoginEffect))
            }
            is InputIsValid -> {
                next(model.loggingIn(), setOf(LoginApi(event.username, event.password) as LoginEffect))
            }
            is InputIsInvalid -> {
                next(model.inputError(event.usernameError, event.passwordError))
            }
            is UserNameEntered -> {
                next(model.clearUsernameError(), setOf(ClearUsernameError as LoginEffect))
            }

            is PasswordEntered -> {
                next(model.clearPasswordError(), setOf(ClearPasswordError))
            }

            is NetworkCallFailed -> {
                next(model.loginFail(), setOf(event.errorMessage?.let { ShowErrorMessageToast(it) } as LoginEffect))
            }

            is NetworkCallSuccess -> {
                dispatch(setOf(ShowHomeScreen, SaveToken(event.token)))
            }
        }
    }

}
