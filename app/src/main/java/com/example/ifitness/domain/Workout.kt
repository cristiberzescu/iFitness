package com.example.ifitness.domain

data class Workout(
    var serviceTitle: String? = null,
    var serviceDescription: String? = null,
    var servicePhone: String? = null,
    var servicePrice: String? = null,

    var serviceLocation: String? = null,
    var serviceOwner: String?= null
)