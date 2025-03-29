package org.example.kmp_shop.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
)