package com.example.ifitness.domain

data class Workout(
    var name: String = "",
    var date: String = "",
    var calories: Int = 0,
    var time: Int = 0,
    var muscularGroup: String = "",
    var description: String = "",
    var video: String = ""
)