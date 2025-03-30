package org.example.kmp_shop.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import org.example.kmp_shop.data.database.entity.ProductEntity

@Dao
interface ProductDAO {

    @Query("select * from products")
    fun getProductsFromCart(): Flow<List<ProductEntity>>

    @Upsert
    suspend fun upsert(product: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)
}