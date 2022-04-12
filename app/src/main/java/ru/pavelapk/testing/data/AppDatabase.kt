package ru.pavelapk.testing.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CacheResultEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cacheResultDao(): CacheResultDao
}