package com.midsummer.orchardjob.di.app

import android.app.Application
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.data.local.OrchardJobDAO
import com.midsummer.orchardjob.data.local.OrchardJobDatabase
import com.midsummer.orchardjob.data.remote.GithubAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */

@Module
class AppModule(private val application: Application) {

    @Provides
    @AppScope
    fun retrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun githubAPI(retrofit: Retrofit) = retrofit.create(GithubAPI::class.java)

    @Provides
    @AppScope
    fun provideLocalDatabase() : OrchardJobDAO =
        OrchardJobDatabase.invoke(application).orchardJobDAO()

    @Provides
    fun application() = application
}