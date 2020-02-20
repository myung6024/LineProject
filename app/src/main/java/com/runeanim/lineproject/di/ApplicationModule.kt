package com.runeanim.lineproject.di

import androidx.room.Room
import com.google.gson.Gson
import com.runeanim.lineproject.local.MemoDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ApplicationModule = module {
    single { Dispatchers.IO }
    single {
        Room.databaseBuilder(
            androidContext(),
            MemoDatabase::class.java,
            "Memos.db"
        ).build()
    }

    single {
        Gson()
    }

    single {
        get<MemoDatabase>().memosDao()
    }
}
