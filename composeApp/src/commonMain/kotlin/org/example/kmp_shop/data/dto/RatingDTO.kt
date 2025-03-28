package org.example.kmp_shop.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RatingDTO(
    val rate: Double,
    val count: Int
)