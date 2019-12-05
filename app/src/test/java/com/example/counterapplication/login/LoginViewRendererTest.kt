package com.example.counterapplication.login

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

class LoginViewRendererTest {

    private val initModel = LoginModel.INIT
    private val loginView = mock<LoginView>()
    private val viewRenderer = LoginViewRenderer(loginView)

    @Test
    fun `it should render blank model`() {
        viewRenderer.render(initModel)

        verifyNoMoreInteractions(loginView)
    }

    @Test
    fun `it should render logging in`() {
        viewRenderer.render(initModel.loggingIn())

        verify(loginView).showProgress()
        verifyNoMoreInteractions(loginView)
    }

    @Test
    fun `it should render login failed state`() {
        viewRenderer.render(initModel.loginFail())
        verify(loginView).hideProgress()
        verifyNoMoreInteractions(loginView)
    }





}