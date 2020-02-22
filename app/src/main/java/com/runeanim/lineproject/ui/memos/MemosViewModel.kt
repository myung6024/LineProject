package com.runeanim.lineproject.ui.memos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.runeanim.lineproject.data.source.local.MemosDao
import com.runeanim.lineproject.data.Result.Success
import com.runeanim.lineproject.data.model.Memo
import com.runeanim.lineproject.data.source.MemosRepository
import com.runeanim.lineproject.util.Event
import java.text.SimpleDateFormat

class MemosViewModel(
    val dateFormat: SimpleDateFormat,
    memosRepository: MemosRepository
) : ViewModel() {
    private val _memos: LiveData<List<Memo>> = memosRepository.observeMemos().map {
        if (it is Success) {
            it.data
        } else {
            emptyList()
        }
    }
    val memos: LiveData<List<Memo>> = _memos

    private val _openMemoEvent = MutableLiveData<Event<Int>>()
    val openMemoEvent: LiveData<Event<Int>> = _openMemoEvent

    fun openMemo(memoId: Int) {
        _openMemoEvent.value = Event(memoId)
    }
}
