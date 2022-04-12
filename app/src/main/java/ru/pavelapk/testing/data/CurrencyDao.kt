package ru.pavelapk.testing.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CacheResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg cacheResultsEntity: CacheResultEntity)

    @Query("SELECT * FROM cache_result")
    suspend fun getAll(): List<CacheResultEntity>

    @Query("SELECT * FROM cache_result WHERE input=:input")
    suspend fun getByInput(input: String): CacheResultEntity?
}