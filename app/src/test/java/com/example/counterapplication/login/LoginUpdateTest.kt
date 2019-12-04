package com.example.counterapplication.login

import com.spotify.mobius.test.NextMatchers.*
import com.spotify.mobius.test.UpdateSpec
import com.spotify.mobius.test.UpdateSpec.assertThatNext
import org.junit.Test

class LoginUpdateTest {
    private val updateSpec = UpdateSpec<LoginModel, LoginEvent, LoginEffect>(LoginUpdate())
    private val username = ""
    private val password = ""

    @Test
    fun `when login button is clicked, then check if input is valid`() {

        updateSpec
            .given(LoginModel.INIT)
            .whenEvent(LoginButtonClicked(username, password))
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(CheckIfValidInput(username, password) as LoginEffect)
                )
            )

    }

    @Test
    fun `when input is invalid, then show input error`() {
        updateSpec
            .given(LoginModel.INIT)
            .whenEvent(InputIsInvalid(ValidationError.INVALID, ValidationError.INVALID))
            .then(
                assertThatNext(
                    hasModel(LoginModel.INIT.inputError(ValidationError.INVALID, ValidationError.INVALID)),
                    hasNoEffects()
                )
            )
    }

    @Test
    fun `when user enters username , then clear username input error`() {
        updateSpec
            .given(LoginModel.INIT.inputError(ValidationError.INVALID, null))
            .whenEvent(UserNameEntered(username))
            .then(
                assertThatNext(
                    hasModel(LoginModel.INIT.inputError(ValidationError.INVALID, null).clearUsernameError()),
                    hasEffects(ClearUsernameError as LoginEffect)
                )
            )

    }

    @Test
    fun `when user enters password, then clear password input error`() {
        updateSpec
            .given(LoginModel.INIT.inputError(null, ValidationError.INVALID))
            .whenEvent(PasswordEntered(password))
            .then(
                assertThatNext(
                    hasModel(LoginModel.INIT.inputError(null, ValidationError.INVALID).clearPasswordError()),
                    hasEffects(ClearPasswordError as LoginEffect)
                )
            )
    }

    @Test
    fun `when input is valid, then perform login`() {
        updateSpec
            .given(LoginModel.INIT)
            .whenEvent(InputIsValid(username, password))
            .then(
                assertThatNext(
                    hasModel(LoginModel.INIT.loggingIn()),
                    hasEffects(LoginApi(username, password) as LoginEffect)
                )
            )
    }

    @Test
    fun `when login api call is failed due to network error, then show network error message`() {
        updateSpec
            .given(LoginModel.INIT.loggingIn())
            .whenEvent(NetworkCallFailed(NetworkErrorMessage.NETWORK_ERROR))
            .then(
                assertThatNext(
                    hasModel(LoginModel.INIT.loggingIn().loginFail()),
                    hasEffects(ShowErrorMessageToast(NetworkErrorMessage.NETWORK_ERROR) as LoginEffect)
                )
            )
    }

    @Test
    fun `when login api call is failed due to input error, then show input error message`() {
        updateSpec
            .given(LoginModel.INIT.loggingIn())
            .whenEvent(NetworkCallFailed(NetworkErrorMessage.INPUT_ERROR))
            .then(
                assertThatNext(
                    hasModel(LoginModel.INIT.loggingIn().loginFail()),
                    hasEffects(ShowErrorMessageToast(NetworkErrorMessage.INPUT_ERROR) as LoginEffect)
                )
            )
    }

    @Test
    fun `when login api call is a success, then show home screen`() {
        updateSpec
            .given(LoginModel.INIT.loggingIn())
            .whenEvent(NetworkCallSuccess(""))
            .then(
                assertThatNext(
                    hasNoModel(),
                    hasEffects(SaveToken("") as LoginEffect, ShowHomeScreen as LoginEffect)
                )
            )
    }

}