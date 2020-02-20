package com.runeanim.lineproject.ui.memodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.MemoEntity

class MemoDetailViewModel(
    private val memosDao: MemosDao
) : ViewModel() {
    private val _memoId = MutableLiveData<Int>()

    private val _memo = _memoId.switchMap { memoId ->
        memosDao.getMemoById(memoId)
    }
    val memo: LiveData<MemoEntity> = _memo

    fun start(memoId: Int) {
        // If we're already loading or already loaded, return (might be a config change)
        if (memoId == _memoId.value) {
            return
        }
        // Trigger the load
        _memoId.value = memoId
    }
}


