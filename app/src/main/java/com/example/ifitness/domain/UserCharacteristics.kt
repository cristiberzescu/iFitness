package com.example.ifitness.domain

object UserCharacteristics {

    private var username: String? = null
    private var email: String? = null

    fun getUsername(): String? {
        return username
    }

    fun setUsername(savedata: String) {
        username = savedata
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(saveemail: String) {
        email = saveemail
    }


}