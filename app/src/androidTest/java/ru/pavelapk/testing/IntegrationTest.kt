package ru.pavelapk.testing

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.pavelapk.testing.data.AppDatabase
import ru.pavelapk.testing.data.CacheResultDao
import ru.pavelapk.testing.data.CacheResultEntity
import java.lang.IndexOutOfBoundsException

@RunWith(AndroidJUnit4::class)
class IntegrationTest {
    private lateinit var cacheResultDao: CacheResultDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        cacheResultDao = db.cacheResultDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testCachePositiveResult() {
        val parityOutlier = ParityOutlier()
        val input = arrayOf(1, 2, 4, 6, 8, 10)
        val inputStr = input.joinToString(",")
        var result: Int? = null
        var error: String? = null
        try {
            result = parityOutlier.find(input)
        } catch (e: IndexOutOfBoundsException) {
            error = e.message
        }

        val cacheResult = CacheResultEntity(
            input = inputStr,
            output = result,
            error = error
        )
        cacheResultDao.insertAll(cacheResult)

        val cacheResultFromDb = cacheResultDao.getByInput(inputStr)
        assertEquals(cacheResult, cacheResultFromDb)
    }

    @Test
    fun testCacheNegativeResult() {
        val parityOutlier = ParityOutlier()
        val input = arrayOf(0, 0, 0)
        val inputStr = input.joinToString(",")
        var result: Int? = null
        var error: String? = null
        try {
            result = parityOutlier.find(input)
        } catch (e: IndexOutOfBoundsException) {
            error = e.message
        }

        val cacheResult = CacheResultEntity(
            input = inputStr,
            output = result,
            error = error
        )
        cacheResultDao.insertAll(cacheResult)

        val cacheResultFromDb = cacheResultDao.getByInput(inputStr)
        assertEquals(cacheResult, cacheResultFromDb)
    }

    @Test
    fun testCacheError() {
        val parityOutlier = ParityOutlier()
        val input = arrayOf(0, 0)
        val inputStr = input.joinToString(",")
        var result: Int? = null
        var error: String? = null
        try {
            result = parityOutlier.find(input)
        } catch (e: IndexOutOfBoundsException) {
            error = e.message
        }

        val cacheResult = CacheResultEntity(
            input = inputStr,
            output = result,
            error = error
        )
        cacheResultDao.insertAll(cacheResult)

        val cacheResultFromDb = cacheResultDao.getByInput(inputStr)
        assertEquals(cacheResult, cacheResultFromDb)
    }

}