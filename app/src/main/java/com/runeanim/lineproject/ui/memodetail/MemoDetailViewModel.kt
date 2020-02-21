package com.runeanim.lineproject.ui.memodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.runeanim.lineproject.base.AttachedImageClickListener
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.AttachedImage
import com.runeanim.lineproject.model.Memo
import com.runeanim.lineproject.util.Event

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

    fun start(memoId: Int) {
        if (memoId == _memoId.value) {
            return
        }
        _memoId.value = memoId
    }

    fun editMemo() {
        _editMemoEvent.value = Event(Unit)
    }

    override fun onAttachedImageClick(attachedImage: AttachedImage) {
        _showImageDetailEvent.value = Event(attachedImage.path)
    }
}


