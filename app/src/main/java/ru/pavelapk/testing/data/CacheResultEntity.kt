package ru.pavelapk.testing.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_result")
data class CacheResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val input: String,
    val output: Int?,
    val error: String?
) {
    override fun equals(other: Any?): Boolean {
        return other is CacheResultEntity &&
                this.input == other.input &&
                this.output == other.output &&
                this.error == other.error
    }
}