package com.example.counterapplication.login

import com.spotify.mobius.Next
import com.spotify.mobius.Update

class LoginUpdate : Update<LoginModel, LoginEvent, LoginEffect> {
    override fun update(model: LoginModel, event: LoginEvent): Next<LoginModel, LoginEffect> {
        return when (event) {
            is LoginButtonClicked -> {
                Next.dispatch(setOf(CheckIfValidInput(event.username, event.password) as LoginEffect))
            }
            is InputIsValid -> {
                Next.next(model.loggingIn(), setOf(LoginApi(event.username, event.password) as LoginEffect))
            }
            is InputIsInvalid -> {
                Next.next(model.inputError(event.usernameError, event.passwordError))
            }
            is UserNameEntered -> {
                Next.next(model.clearUsernameError(), setOf(ClearUsernameError as LoginEffect))
            }

            is PasswordEntered -> {
                Next.next(model.clearPasswordError(), setOf(ClearPasswordError))
            }

            is NetworkCallFailed -> {
                Next.next(model.loginFail(), setOf(event.errorMessage?.let { ShowErrorMessageToast(it) } as LoginEffect))
            }

            is NetworkCallSuccess -> {
                Next.dispatch(setOf(ShowHomeScreen, SaveToken(event.token)))
            }
        }
    }

}
