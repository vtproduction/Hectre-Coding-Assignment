package com.midsummer.orchardjob.di.viewModel

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@MapKey
annotation class ViewModelKeys(val value: KClass<out ViewModel>)
