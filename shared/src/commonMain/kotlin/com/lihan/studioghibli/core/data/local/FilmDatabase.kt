package com.lihan.studioghibli.core.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.lihan.studioghibli.core.data.local.dao.FilmDao
import com.lihan.studioghibli.core.data.local.entity.FilmEntity

@Database(
    entities = [FilmEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListTypeConverter::class)
@ConstructedBy(FilmDatabaseConstructor::class)
abstract class FilmDatabase : RoomDatabase() {
    abstract val filmDao: FilmDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object FilmDatabaseConstructor : RoomDatabaseConstructor<FilmDatabase> {
    override fun initialize(): FilmDatabase
}
