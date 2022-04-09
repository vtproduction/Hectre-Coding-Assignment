package com.midsummer.orchardjob.di.activity

import androidx.appcompat.app.AppCompatActivity
import com.midsummer.orchardjob.di.presentation.PresentationComponent
import com.midsummer.orchardjob.di.presentation.PresentationModule
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
@Subcomponent(modules = [ActivityModule::class])
@ActivityScope
interface ActivityComponent {

    fun newPresentationComponent(presentationModule: PresentationModule) : PresentationComponent

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: AppCompatActivity) : Builder

        fun build() :  ActivityComponent
    }
}