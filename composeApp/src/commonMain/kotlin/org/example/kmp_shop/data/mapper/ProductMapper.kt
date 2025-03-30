package org.example.kmp_shop.data.mapper

import org.example.kmp_shop.data.database.entity.ProductEntity
import org.example.kmp_shop.data.network.dto.ProductDTO
import org.example.kmp_shop.data.network.dto.RatingDTO
import org.example.kmp_shop.domain.model.Product
import org.example.kmp_shop.domain.model.Rating

fun ProductDTO.toModel() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
    rating = rating.toModel()
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

fun RatingDTO.toModel() = Rating(
    rate = rate,
    count = count
)

fun ProductEntity.toModel() = Product(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
)

fun Product.toEntity() = ProductEntity(
    id = id,
    title = title,
    price = price,
    description = description,
    category = category,
    image = image,
)