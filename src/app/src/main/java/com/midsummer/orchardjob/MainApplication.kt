package com.midsummer.orchardjob

import android.app.Application
import com.midsummer.orchardjob.di.app.AppComponent
import com.midsummer.orchardjob.di.app.AppModule
import com.midsummer.orchardjob.di.app.DaggerAppComponent

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class MainApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()

    }
}