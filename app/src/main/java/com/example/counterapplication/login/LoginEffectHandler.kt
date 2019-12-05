package com.example.counterapplication.login

import android.widget.Toast
import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import kotlin.coroutines.coroutineContext

class LoginEffectHandler(
    private val loginApiStub: LoginApiStub
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
                    output.accept(NetworkCallFailed(value.errorMessage))
                }

            }
         }

         override fun dispose() {
         }
     }
    }
}