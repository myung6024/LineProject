package com.runeanim.lineproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.runeanim.lineproject.local.MemosDao
import com.runeanim.lineproject.model.MemoEntity

class MainViewModel(
    private val memosDao: MemosDao
) : ViewModel() {
    // TODO: Implement the ViewModel
    private val _memos: LiveData<List<MemoEntity>> by lazy { memosDao.getMemos() }
    val memos: LiveData<List<MemoEntity>> = _memos
}
