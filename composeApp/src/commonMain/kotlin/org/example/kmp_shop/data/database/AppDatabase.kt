package org.example.kmp_shop.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.kmp_shop.data.database.dao.ProductDAO
import org.example.kmp_shop.data.database.entity.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val productDAO: ProductDAO

    companion object {
        const val DATABASE_NAME = "my_room.db"
    }
}