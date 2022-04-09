package com.midsummer.orchardjob.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.midsummer.orchardjob.MainApplication
import com.midsummer.orchardjob.di.presentation.PresentationModule

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
open class BaseActivity : AppCompatActivity() {

    private val appComponent get() = (application as MainApplication).appComponent

    val activityComponent by lazy {
        appComponent.newActivityComponentBuilder()
            .activity(this)
            .build()
    }

    private val presentationComponent by lazy {
        activityComponent.newPresentationComponent(PresentationModule(this))
    }

    protected val injector get() = presentationComponent
}