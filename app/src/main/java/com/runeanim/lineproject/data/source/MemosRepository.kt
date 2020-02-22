package com.runeanim.lineproject.data.source

import androidx.lifecycle.LiveData
import com.runeanim.lineproject.data.Result
import com.runeanim.lineproject.data.model.Memo

interface MemosRepository {

    suspend fun saveMemo(memo: Memo)

    fun observeMemos(): LiveData<Result<List<Memo>>>

    fun observeMemo(memoId: Int): LiveData<Result<Memo>>

    suspend fun getMemo(memoId: Int): Result<Memo>

    suspend fun deleteMemo(memoId: Int)
}
