package com.runeanim.lineproject.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.runeanim.lineproject.model.Memo

@Dao
interface MemosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Query("SELECT * FROM memos ORDER BY itemOrder ASC")
    fun getMemos(): LiveData<List<Memo>>

    @Query("SELECT * FROM memos WHERE id = :id")
    fun getMemoById(id: String): LiveData<Memo>
}