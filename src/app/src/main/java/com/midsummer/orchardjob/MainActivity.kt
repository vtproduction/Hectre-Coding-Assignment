package com.midsummer.orchardjob

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        screenNavigator.testLaunch()
        dialogNavigator.showServerErrorDialog()
        fetchJobs()
    }

    private fun fetchJobs() {
        coroutineScope.launch {
            val r = repository.fetchData()
        }
    }
}