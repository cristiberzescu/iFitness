package com.example.ifitness.domain

data class Exercise(
    var name: String = "",
    var series: List<Series>? = null
)