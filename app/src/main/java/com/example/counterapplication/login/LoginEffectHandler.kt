package com.example.counterapplication.login

import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer

class LoginEffectHandler(
    private val loginApiStub: LoginApiStub,
    private val loginViewActions: LoginViewActions
) : Connectable<LoginEffect, LoginEvent> {
    override fun connect(output: Consumer<LoginEvent>): Connection<LoginEffect> {
     return object : Connection<LoginEffect> {
         override fun accept(value: LoginEffect) {
            when(value) {
                is CheckIfValidInput -> {
                    if(value.username.isEmpty() && value.password.isEmpty()){
                        output.accept(InputIsInvalid(ValidationError.INVALID, ValidationError.INVALID))
                    }
                    else if(value.username.isEmpty()){
                        output.accept(InputIsInvalid(ValidationError.INVALID, null))
                    }
                    else if(value.password.isEmpty()){
                        output.accept(InputIsInvalid(null, ValidationError.INVALID))
                    }
                    else{
                        output.accept(InputIsValid(value.username, value.password))
                    }
                }
                is LoginApi -> {
                    val authToken = loginApiStub.login(value.username, value.password)
                    output.accept(NetworkCallSuccess(authToken))
                }

                is ShowErrorMessageToast -> {
                    loginViewActions.showErrorMessageToast(value.errorMessage)
                }

                is ClearUsernameError -> {
                    loginViewActions.clearUsernameError()
                }

                is ClearPasswordError -> {
                    loginViewActions.clearPasswordError()
                }

                is ShowHomeScreen -> {
                    loginViewActions.showHomeScreen()
                }

                is SaveToken -> {
                    loginViewActions.saveToken(value.token)
                }

            }
         }

         override fun dispose() {
         }
     }
    }
}