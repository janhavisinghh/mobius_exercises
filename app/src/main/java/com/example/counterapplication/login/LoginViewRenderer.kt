package com.example.counterapplication.login

import android.util.Log

class LoginViewRenderer(private val loginView: LoginView) {

    fun render(model: LoginModel) {
        Log.d("LoginViewRenderer", model.toString())
        if (model.loginStatus == LoginStatus.IN_PROGRESS) {
            loginView.showProgress()
        } else if (model.loginStatus == LoginStatus.FAIL) {
            loginView.hideProgress()
        }

        if(model.usernameInputError == ValidationError.INVALID){
            loginView.showUsernameError()
        }

        if(model.passwordInputError == ValidationError.INVALID){
            loginView.showPasswordError()
        }
    }
}
