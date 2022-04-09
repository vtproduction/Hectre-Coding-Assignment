package com.midsummer.orchardjob.di.presentation

import com.midsummer.orchardjob.screens.mainScreen.MainActivity
import com.midsummer.orchardjob.screens.loadingScreen.LoadingActivity
import dagger.Subcomponent

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
@Subcomponent(modules = [PresentationModule::class])
@PresentationScope
interface PresentationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(loadingActivity: LoadingActivity)
}