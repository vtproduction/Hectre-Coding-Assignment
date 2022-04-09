package com.midsummer.orchardjob.di.activity

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.midsummer.orchardjob.screens.common.ScreenNavigator
import com.midsummer.orchardjob.screens.common.ScreensNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@Module
abstract class ActivityModule {

    @Binds
    abstract fun screenNavigator(screensNavigatorImpl: ScreensNavigatorImpl) : ScreenNavigator

    companion object {
        @Provides
        fun layoutInflater(activity: AppCompatActivity) = LayoutInflater.from(activity)

        @Provides
        fun fragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager
    }
}