package com.patigayon.meditationguideapp

data class MeditationTechnique(
    val name: String = "",
    val routine: String = "",
    val photo: String = "", // URL to the image
    val category: String = "",
    val sessions: Int = 0
)
