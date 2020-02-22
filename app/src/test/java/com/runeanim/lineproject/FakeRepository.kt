package com.runeanim.lineproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.runeanim.lineproject.data.Result
import com.runeanim.lineproject.data.Result.Error
import com.runeanim.lineproject.data.Result.Success
import com.runeanim.lineproject.data.model.Memo
import com.runeanim.lineproject.data.source.MemosRepository
import kotlinx.coroutines.runBlocking
import java.util.*

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
class FakeRepository : MemosRepository {
    var memosServiceData: LinkedHashMap<Int, Memo> = LinkedHashMap()

    private val observableMemos = MutableLiveData<Result<List<Memo>>>()

    private var shouldReturnError = false

    override suspend fun saveMemo(memo: Memo) {
        memosServiceData[memo.id] = memo
    }

    override fun observeMemos(): LiveData<Result<List<Memo>>> {
        runBlocking { observableMemos.value = getMemos() }
        return observableMemos
    }

    override fun observeMemo(memoId: Int): LiveData<Result<Memo>> {
        runBlocking { observableMemos.value = getMemos() }
        return observableMemos.map { memos ->
            when (memos) {
                is Result.Loading -> Result.Loading
                is Error -> Error(memos.exception)
                is Success -> {
                    val task = memos.data.firstOrNull { it.id == memoId }
                        ?: return@map Error(Exception("Not found"))
                    Success(task)
                }
            }
        }
    }

    override suspend fun getMemo(memoId: Int): Result<Memo> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        memosServiceData[memoId]?.let {
            return Success(it)
        }
        return Error(Exception("Could not find task"))
    }

    override suspend fun deleteMemo(memoId: Int) {
        memosServiceData.remove(memoId)
        observableMemos.value = getMemos()
    }

    private fun getMemos(): Result<List<Memo>> {
        if (shouldReturnError) {
            return Error(Exception("Test exception"))
        }
        return Success(memosServiceData.values.toList())
    }

}
