package com.midsummer.orchardjob.screens.common

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.midsummer.orchardjob.di.activity.ActivityScope
import com.midsummer.orchardjob.screens.mainScreen.MainActivity
import javax.inject.Inject

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

interface ScreenNavigator{
    fun navigateBack()
    fun goToMain()
}

@ActivityScope
class ScreensNavigatorImpl @Inject constructor(private val activity: AppCompatActivity) : ScreenNavigator {

    override fun navigateBack() {
        activity.onBackPressed()
    }

    override fun goToMain() {
        MainActivity.start(activity)
    }
}