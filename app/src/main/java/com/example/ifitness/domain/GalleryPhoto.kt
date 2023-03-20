package com.example.ifitness.domain

data class GalleryPhoto(
    var imageUrl: String = "",
    var date: String = "",
    var measurements: Map<String, String> = mapOf()
)