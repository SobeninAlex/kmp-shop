package org.example.kmp_shop.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Query("select * from products")
    fun productsFromCart(): Flow<List<ProductEntity>>

    @Upsert
    suspend fun upsert(product: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)
}