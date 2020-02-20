package com.runeanim.lineproject.di

import com.runeanim.lineproject.ui.addeditmemo.AddEditMemoViewModel
import com.runeanim.lineproject.ui.main.MainViewModel
import com.runeanim.lineproject.ui.memodetail.MemoDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { AddEditMemoViewModel(get()) }
    viewModel { MemoDetailViewModel(get()) }
}
