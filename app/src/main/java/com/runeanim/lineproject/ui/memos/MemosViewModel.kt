package com.runeanim.lineproject.ui.memos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.Memo
import com.runeanim.lineproject.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemosViewModel(
    private val memosDao: MemosDao
) : ViewModel() {
    private val _memos: LiveData<List<Memo>> by lazy { memosDao.getMemos() }
    val memos: LiveData<List<Memo>> = _memos

    private val _openMemoEvent = MutableLiveData<Event<Int>>()
    val openMemoEvent: LiveData<Event<Int>> = _openMemoEvent

    fun openMemo(memoId: Int) {
        _openMemoEvent.value = Event(memoId)
    }

    fun removeMemo(memoId: Int): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            memosDao.removeMemoById(memoId)
        }
        return true
    }
}
