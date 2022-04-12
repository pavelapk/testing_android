package ru.pavelapk.testing.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg cacheResultsEntity: CacheResultEntity)

    @Query("SELECT * FROM cache_result")
    fun getAll(): List<CacheResultEntity>

    @Query("SELECT * FROM cache_result WHERE input=:input")
    fun getByInput(input: String): CacheResultEntity
}