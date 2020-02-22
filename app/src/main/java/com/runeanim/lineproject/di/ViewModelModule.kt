package com.runeanim.lineproject.di

import com.runeanim.lineproject.ui.addeditmemo.AddEditMemoViewModel
import com.runeanim.lineproject.ui.imagedetail.ImageDetailViewModel
import com.runeanim.lineproject.ui.memos.MemosViewModel
import com.runeanim.lineproject.ui.memodetail.MemoDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MemosViewModel(get(), get()) }
    viewModel { AddEditMemoViewModel(get()) }
    viewModel { MemoDetailViewModel(get()) }
    viewModel { ImageDetailViewModel() }
}
