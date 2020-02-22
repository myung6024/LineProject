package com.runeanim.lineproject.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.runeanim.lineproject.data.Result
import com.runeanim.lineproject.data.Result.Error
import com.runeanim.lineproject.data.Result.Success
import com.runeanim.lineproject.data.model.Memo
import com.runeanim.lineproject.data.source.MemosDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemosLocalDataSource(
    private val memosDao: MemosDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MemosDataSource {
    override suspend fun getMemo(memoId: Int): Result<Memo> = withContext(ioDispatcher) {
        try {
            val memo = memosDao.getMemoById(memoId)
            if (memo != null) {
                return@withContext Success(memo)
            } else {
                return@withContext Error(Exception("Task not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun deleteMemo(memoId: Int) = withContext<Unit>(ioDispatcher) {
        memosDao.removeMemoById(memoId)
    }

    override suspend fun saveMemo(memo: Memo) = withContext(ioDispatcher) {
        memosDao.insertMemo(memo)
    }

    override fun observeMemos(): LiveData<Result<List<Memo>>> {
        return memosDao.observeMemos().map {
            Success(it)
        }
    }

    override fun observeMemo(memoId: Int): LiveData<Result<Memo>> {
        return memosDao.observeMemoById(memoId).map {
            Success(it)
        }
    }
}
