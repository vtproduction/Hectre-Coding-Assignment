package com.midsummer.orchardjob.di.presentation

import com.midsummer.orchardjob.MainActivity
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
}