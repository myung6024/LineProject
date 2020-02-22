package com.runeanim.lineproject.data.source

import androidx.lifecycle.LiveData
import com.runeanim.lineproject.data.Result
import com.runeanim.lineproject.data.model.Memo
import com.runeanim.lineproject.data.source.local.MemosLocalDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DefaultMemosRepository(
    private val memosLocalDataSource: MemosLocalDataSource
) : MemosRepository {
    override suspend fun saveMemo(memo: Memo) {
        return memosLocalDataSource.saveMemo(memo)
    }

    override fun observeMemos(): LiveData<Result<List<Memo>>> {
        return memosLocalDataSource.observeMemos()
    }

    override fun observeMemo(memoId: Int): LiveData<Result<Memo>> {
        return memosLocalDataSource.observeMemo(memoId)
    }

    override suspend fun getMemo(memoId: Int): Result<Memo> {
        return memosLocalDataSource.getMemo(memoId)
    }

    override suspend fun deleteMemo(memoId: Int) {
        coroutineScope {
            launch { memosLocalDataSource.deleteMemo(memoId) }
        }
    }
}
