package com.example.counterapplication.login

class LoginViewRenderer(private val loginView: LoginView) {

    fun render(model: LoginModel) {
        if (model.loginStatus == LoginStatus.IN_PROGRESS) {
            loginView.showProgress()
        } else if (model.loginStatus == LoginStatus.FAIL) {
            loginView.hideProgress()
        }
    }
}
