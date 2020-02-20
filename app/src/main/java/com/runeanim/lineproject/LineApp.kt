package com.runeanim.lineproject

import android.app.Application
import com.runeanim.lineproject.di.ApplicationModule
import com.runeanim.lineproject.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LineApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LineApp)
            modules(
                listOf(
                    ApplicationModule,
                    ViewModelModule
                )
            )
        }
    }
}
