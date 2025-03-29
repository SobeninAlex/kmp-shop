package org.example.kmp_shop.data.database

import androidx.room.RoomDatabase

expect class AppDatabaseFactory {

    fun create(): RoomDatabase.Builder<AppDatabase>
}