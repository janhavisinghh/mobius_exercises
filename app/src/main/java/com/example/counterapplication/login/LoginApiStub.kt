package com.example.counterapplication.login

interface LoginApiStub {
    fun login(username: String, password: String): String
}

class LoginApiStubImpl : LoginApiStub {
    override fun login(username: String, password: String): String {
        return "real-auth-token"
    }
}