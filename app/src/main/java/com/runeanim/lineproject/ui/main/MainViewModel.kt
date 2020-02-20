package com.runeanim.lineproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.Memo

class MainViewModel(
    private val memosDao: MemosDao
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _memos: LiveData<List<Memo>> by lazy { memosDao.getMemos() }
    val memos: LiveData<List<Memo>> = _memos
}
