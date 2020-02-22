package com.runeanim.lineproject.ui.memodetail

import androidx.lifecycle.*
import com.runeanim.lineproject.base.AttachedImageClickListener
import com.runeanim.lineproject.data.Result.Success
import com.runeanim.lineproject.data.model.AttachedImage
import com.runeanim.lineproject.data.model.Memo
import com.runeanim.lineproject.data.source.MemosRepository
import com.runeanim.lineproject.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoDetailViewModel(
    private val memosRepository: MemosRepository
) : ViewModel(), AttachedImageClickListener {

    private val _memoId = MutableLiveData<Int>()

    private val _memo = _memoId.switchMap { memoId ->
        memosRepository.observeMemo(memoId).map {
            if (it is Success) {
                it.data
            } else {
                null
            }
        }
    }
    val memo: LiveData<Memo?> = _memo

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
            viewModelScope.launch {
                memosRepository.deleteMemo(memoId)
            }
        }
        _closeEvent.value = Event(Unit)
        return true
    }

    override fun onAttachedImageClick(attachedImage: AttachedImage) {
        _showImageDetailEvent.value = Event(attachedImage.path)
    }
}


