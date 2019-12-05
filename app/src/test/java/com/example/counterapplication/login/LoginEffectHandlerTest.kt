package com.example.counterapplication.login

import com.example.counterapplication.login.ValidationError.INVALID
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.spotify.mobius.Connection
import com.spotify.mobius.test.RecordingConsumer
import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginEffectHandlerTest {

    private val loginApiStub = mock<LoginApiStub>()
    private val viewActions = mock<LoginViewActions>()
    private val effectHandler = LoginEffectHandler(loginApiStub, viewActions)
    private val consumer = RecordingConsumer<LoginEvent>()
    private lateinit var connection: Connection<LoginEffect>

    @Before
    fun setup() {
        connection = effectHandler.connect(consumer)
    }

    @After
    fun dispose() {
        connection.dispose()
    }

    @Test
    fun `when input is invalid, then return input is invalid`() {
        connection.accept(CheckIfValidInput("", ""))

        consumer.assertValues(InputIsInvalid(INVALID, INVALID))
    }

    @Test
    fun `when input is valid, then return input is valid`() {
        connection.accept(CheckIfValidInput("user", "pass"))

        consumer.assertValues(InputIsValid("user", "pass"))
    }

    @Test
    fun `when login is success, then return login success event`() {
        val username = "username"
        val password = "password"

        whenever(loginApiStub.login(username, password)).thenReturn("real-auth-token")

        connection.accept(LoginApi(username, password))

        consumer.assertValues(NetworkCallSuccess("real-auth-token"))
    }

    @Test
    fun `when login is failed, show error message toast`() {
        connection.accept(ShowErrorMessageToast(NetworkErrorMessage.INPUT_ERROR))

        verify(viewActions).showErrorMessageToast(NetworkErrorMessage.INPUT_ERROR)
    }

    @Test
    fun `when username is entered, clear username not entered error`() {
        connection.accept(ClearUsernameError)
        verify(viewActions).clearUsernameError()
    }

    @Test
    fun `when password is entered, clear password not entered error`() {
        connection.accept(ClearPasswordError)
        verify(viewActions).clearPasswordError()
    }

    @Test
    fun `when network call is successful, then show homescreen`() {
        connection.accept(ShowHomeScreen)
        verify(viewActions).showHomeScreen()
    }

    @Test
    fun `when network call is successful, then save token`() {
        connection.accept(SaveToken("real-auth-token"))
        verify(viewActions).saveToken("real-auth-token")
    }
}