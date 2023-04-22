package com.example.ifitness.domain

data class User(
    var userEmail: String? = null,
    var userName: String? = null,
    var userPassword: String? = null,
    var dateCalories: Date? = null,
    var dateWorkout: Date? = null
)