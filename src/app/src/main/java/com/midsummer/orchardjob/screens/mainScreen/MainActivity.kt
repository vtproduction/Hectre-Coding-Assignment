package com.midsummer.orchardjob.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.Constants.TAG
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.databinding.ActivityMainBinding
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.screens.common.ScreenNavigator
import com.midsummer.orchardjob.screens.common.activities.BaseActivity
import com.midsummer.orchardjob.screens.common.dialogs.DialogNavigator
import com.midsummer.orchardjob.screens.common.dialogs.GeneralConfirmDialog
import com.midsummer.orchardjob.screens.common.jobs.JobDetailViewController
import com.midsummer.orchardjob.screens.common.jobs.JobsViewController
import com.midsummer.orchardjob.screens.common.rvcells.RVAdapter
import com.midsummer.orchardjob.screens.common.viewController.ViewControllerFactory
import com.midsummer.orchardjob.screens.common.viewModel.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var screenNavigator: ScreenNavigator
    @Inject lateinit var dialogNavigator: DialogNavigator
    @Inject lateinit var viewControllerFactory: ViewControllerFactory

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        binding.toolbar.setNavigateUpListener {
            screenNavigator.navigateBack()
        }

        setupRecycleView()
        observeLiveData()
    }

    private fun setupRecycleView(){
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = RVAdapter(this@MainActivity, viewModel)
        }
    }

    private fun observeLiveData() {

        viewModel.viewData.observe(this){
            it?.forEach { item ->
                Log.d(TAG, "observeLiveData: ${item.type} : ${item.jobType} ${item.viewData?.currentSelectedButton}" +
                        " ${item.viewData?.currentPriceRate}")
            }
            Log.d(TAG, "observeLiveData: ==================")
            (binding.recyclerView.adapter as RVAdapter).submitList(it)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveData()
        super.onSaveInstanceState(outState)
    }

    companion object{
        fun start(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}