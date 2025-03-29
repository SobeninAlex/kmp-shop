package org.example.kmp_shop.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RatingDTO(
    val rate: Double,
    val count: Int
)