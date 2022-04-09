package com.midsummer.orchardjob.di.app

import com.midsummer.orchardjob.di.activity.ActivityComponent
import dagger.Component

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponentBuilder() : ActivityComponent.Builder
}