package com.runeanim.lineproject.ui.memodetail

import androidx.lifecycle.*
import com.runeanim.lineproject.base.AttachedImageClickListener
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.AttachedImage
import com.runeanim.lineproject.model.Memo
import com.runeanim.lineproject.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoDetailViewModel(
    private val memosDao: MemosDao
) : ViewModel(), AttachedImageClickListener {

    private val _memoId = MutableLiveData<Int>()

    private val _memo = _memoId.switchMap { memoId ->
        memosDao.observeMemoById(memoId)
    }
    val memo: LiveData<Memo> = _memo

    private val _editMemoEvent = MutableLiveData<Event<Unit>>()
    val editMemoEvent: LiveData<Event<Unit>> = _editMemoEvent

    private val _showImageDetailEvent = MutableLiveData<Event<String>>()
    val showImageDetailEvent: LiveData<Event<String>> = _showImageDetailEvent

    private val _closeEvent = MutableLiveData<Event<Unit>>()
    val closeEvent: LiveData<Event<Unit>> = _closeEvent

    fun start(memoId: Int) {
        if (memoId == _memoId.value) {
            return
        }
        _memoId.value = memoId
    }

    fun editMemo() {
        _editMemoEvent.value = Event(Unit)
    }

    fun close() {
        _closeEvent.value = Event(Unit)
    }

    fun removeMemo(): Boolean {
        val memoId = _memoId.value
        memoId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                memosDao.removeMemoById(memoId)
            }
        }
        _closeEvent.value = Event(Unit)
        return true
    }

    override fun onAttachedImageClick(attachedImage: AttachedImage) {
        _showImageDetailEvent.value = Event(attachedImage.path)
    }
}


