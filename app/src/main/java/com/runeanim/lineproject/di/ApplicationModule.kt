package com.runeanim.lineproject.di

import androidx.room.Room
import com.google.gson.Gson
import com.runeanim.lineproject.R
import com.runeanim.lineproject.data.source.DefaultMemosRepository
import com.runeanim.lineproject.data.source.MemosRepository
import com.runeanim.lineproject.data.source.local.MemoDatabase
import com.runeanim.lineproject.data.source.local.MemosLocalDataSource
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.*

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

    single {
        SimpleDateFormat(androidContext().getString(R.string.memo_date_format), Locale.KOREA)
    }

    single { MemosLocalDataSource(get(), get()) }
    single { DefaultMemosRepository(get()) as MemosRepository }
}
