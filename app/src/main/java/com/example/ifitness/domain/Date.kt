package com.example.ifitness.domain

data class Date(
    var totalCalories: Int? = null,
    var totalProtein: Int? = null,
    var totalCarbs: Int? = null,
    var totalFats: Int? = null,
    var food: Food? = null,
    var workout: Workout? = null
)