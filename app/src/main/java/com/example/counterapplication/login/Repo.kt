package com.example.counterapplication.login

interface Repo {
    fun saveToken(token: String) : String
}

class SaveTokenRepo : Repo{
    override fun saveToken(token: String): String {
       return ""
    }
}


