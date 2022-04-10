package com.midsummer.orchardjob.di.viewModel

import androidx.lifecycle.ViewModel
import com.midsummer.orchardjob.screens.loadingScreen.LoadingViewModel
import com.midsummer.orchardjob.screens.mainScreen.MainViewModel
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
    abstract fun newViewModel1(viewModel: LoadingViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKeys(MainViewModel::class)
    abstract fun newViewModel2(viewModel: MainViewModel) : ViewModel
}