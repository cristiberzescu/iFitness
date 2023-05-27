package com.example.ifitness.domain

object UserCharacteristics {

    private var username: String? = null
    private var email: String? = null
    private var weight: String? = null

    fun getUsername(): String? {
        return username
    }

    fun setUsername(sendName: String) {
        username = sendName
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(sendEmail: String) {
        email = sendEmail
    }

    fun getWeight(): String? {
        return weight
    }

    fun setWeight(sendWeight: String) {
        weight = sendWeight
    }


}