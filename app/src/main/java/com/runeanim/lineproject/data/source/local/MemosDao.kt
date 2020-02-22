package com.runeanim.lineproject.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.runeanim.lineproject.data.model.Memo

@Dao
interface MemosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: Memo)

    @Query("SELECT * FROM memos ORDER BY itemOrder DESC")
    fun observeMemos(): LiveData<List<Memo>>

    @Query("SELECT * FROM memos WHERE id = :id")
    suspend fun getMemoById(id: Int): Memo?

    @Query("SELECT * FROM memos WHERE id = :id")
    fun observeMemoById(id: Int): LiveData<Memo>

    @Query("DELETE FROM memos WHERE id = :id")
    suspend fun removeMemoById(id: Int): Int
}
