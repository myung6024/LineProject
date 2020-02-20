package com.runeanim.lineproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.MemoEntity
import com.runeanim.lineproject.util.Event

class MainViewModel(
    private val memosDao: MemosDao
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _memos: LiveData<List<MemoEntity>> by lazy { memosDao.getMemos() }
    val memos: LiveData<List<MemoEntity>> = _memos

    private val _openMemoEvent = MutableLiveData<Event<Int>>()
    val openMemoEvent: LiveData<Event<Int>> = _openMemoEvent

    fun openTask(memoId: Int) {
        _openMemoEvent.value = Event(memoId)
    }
}
