package com.runeanim.lineproject.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.runeanim.lineproject.model.Memo

@Database(entities = [Memo::class], version = 1, exportSchema = false)
@TypeConverters(RoomConverter::class)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memosDao(): MemosDao
}