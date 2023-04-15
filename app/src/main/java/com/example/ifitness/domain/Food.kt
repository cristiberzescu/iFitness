package com.example.ifitness.domain

data class Food(
    var name: String = "",
    var calories: Int = 0,
    var protein: Float = 0F,
    var carbs: Float = 0F,
    var fats: Float = 0F,
    var grams: Int? = null,
    var currentTime: Long? = null

)