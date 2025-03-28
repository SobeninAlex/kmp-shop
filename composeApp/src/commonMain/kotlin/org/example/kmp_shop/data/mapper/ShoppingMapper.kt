package org.example.kmp_shop.data.mapper

import org.example.kmp_shop.data.dto.ProductDTO
import org.example.kmp_shop.data.dto.RatingDTO
import org.example.kmp_shop.domain.entity.Product
import org.example.kmp_shop.domain.entity.Rating

fun ProductDTO.toEntity() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating.toEntity()
)

fun Product.toDTO() = ProductDTO(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating.toDTO()
)

fun Rating.toDTO() = RatingDTO(
    rate = rate,
    count = count
)

fun RatingDTO.toEntity() = Rating(
    rate = rate,
    count = count
)