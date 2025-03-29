package org.example.kmp_shop.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


actual class AppDatabaseFactory(
    private val context: Context
) {

    actual fun create(): RoomDatabase.Builder<AppDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(AppDatabase.DATABASE_NAME)
        return Room.databaseBuilder<AppDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}