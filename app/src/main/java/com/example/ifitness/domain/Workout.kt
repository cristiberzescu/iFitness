package com.example.ifitness.domain

data class Workout(
    var name: String = "",
    var date: String = "",
    var exercises: List<Exercise>? = null
)

