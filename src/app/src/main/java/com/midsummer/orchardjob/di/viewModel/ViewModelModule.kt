package com.midsummer.orchardjob.di.viewModel

import androidx.lifecycle.ViewModel
import com.midsummer.orchardjob.screens.loadingScreen.LoadingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by nienle on 10,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKeys(LoadingViewModel::class)
    abstract fun loadingViewModel(viewModel: LoadingViewModel) : ViewModel
}