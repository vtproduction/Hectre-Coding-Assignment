package com.midsummer.orchardjob.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.data.OrchardJobRepository
import com.midsummer.orchardjob.screens.common.ScreenNavigator
import com.midsummer.orchardjob.screens.common.activities.BaseActivity
import com.midsummer.orchardjob.screens.common.dialogs.DialogNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity() {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @Inject lateinit var screenNavigator: ScreenNavigator
    @Inject lateinit var dialogNavigator: DialogNavigator
    @Inject lateinit var repository: OrchardJobRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onResume() {
        super.onResume()
        dialogNavigator.showServerErrorDialog()
        fetchJobs()
    }

    private fun fetchJobs() {
        coroutineScope.launch {
            val r = repository.fetchData()
        }
    }

    companion object{
        fun start(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}