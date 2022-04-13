package com.midsummer.orchardjob.screens.mainScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.databinding.ActivityMainBinding
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.screens.common.ScreenNavigator
import com.midsummer.orchardjob.screens.common.activities.BaseActivity
import com.midsummer.orchardjob.screens.common.dialogs.DialogNavigator
import com.midsummer.orchardjob.screens.common.dialogs.GeneralConfirmDialog
import com.midsummer.orchardjob.screens.common.jobs.JobDetailViewController
import com.midsummer.orchardjob.screens.common.jobs.JobsViewController
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

    private lateinit var pruningJobsVC: JobsViewController
    private lateinit var thinningJobsVC: JobsViewController

    private val controllerList : MutableList<JobsViewController> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        pruningJobsVC =
            viewControllerFactory.newJobsViewController(window.decorView.rootView as ViewGroup)
        thinningJobsVC =
            viewControllerFactory.newJobsViewController(window.decorView.rootView as ViewGroup)

        binding.toolbar.setNavigateUpListener {
            screenNavigator.navigateBack()
        }


        binding.container.addView(pruningJobsVC.rootView)
        binding.container.addView(thinningJobsVC.rootView)

        val confirmBtn = layoutInflater.inflate(R.layout.item_confirm_btn, window.decorView.rootView as ViewGroup, false)

        binding.container.addView(confirmBtn)
        confirmBtn.setOnClickListener {
            dialogNavigator.showGeneralConfirmDialog(R.string.do_you_want_to_update_jobs,
                object : GeneralConfirmDialog.Callback {
                    override fun onConfirm() {
                        Toast.makeText(this@MainActivity, getString(R.string.updated), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDismiss() {}
                })
        }

        controllerList.add(pruningJobsVC)
        controllerList.add(thinningJobsVC)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.pruningJobs.observe(this) {
            it?.forEach { job ->
                Log.d(
                    Constants.TAG,
                    "observeLiveData: ${job.id} " +
                            job.row.joinToString(separator = " - ", transform = { r -> "${r.rowId}:${r.current}" })
                )
            }
            bindJobs(it, Constants.JOB_TYPE_PRUNING)
        }
        viewModel.thinningJobs.observe(this) {
            bindJobs(it, Constants.JOB_TYPE_THINNING)
        }
        viewModel.rateForAllPruning.observe(this){
            pruningJobsVC.applyRateToAllStaff(it)
        }
        viewModel.rateForAllThinning.observe(this){
            thinningJobsVC.applyRateToAllStaff(it)
        }
    }

    private fun bindJobs (jobs: List<OrchardJob>, type: Int){
        val jobDetailViewControllerList : MutableList<JobDetailViewController> = arrayListOf()
        if (type == Constants.JOB_TYPE_PRUNING){
            pruningJobsVC.bindJobs(jobs, type)
        }

        if (type == Constants.JOB_TYPE_THINNING){
            thinningJobsVC.bindJobs(jobs, type)
        }
        jobs.forEach {  job ->
            val jobDetailViewController = viewControllerFactory
                .newJobDetailController(window.decorView.rootView as ViewGroup)

            val orchardRowController = viewControllerFactory
               .newOrchardRowController(window.decorView.rootView as ViewGroup)

            orchardRowController.bindJob(job)
            jobDetailViewController.bindJob(job, orchardRowController)
            jobDetailViewControllerList.add(jobDetailViewController)

        }
        if (type == Constants.JOB_TYPE_PRUNING){
            pruningJobsVC.bindJobDetails(jobDetailViewControllerList, jobs)
        }

        if (type == Constants.JOB_TYPE_THINNING){
            thinningJobsVC.bindJobDetails(jobDetailViewControllerList, jobs)
        }

        registerListeners()
    }


    override fun onStart() {
        super.onStart()
        registerListeners()
    }

    override fun onStop() {
        super.onStop()
        unregisterListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveData()
        super.onSaveInstanceState(outState)
    }

    private fun registerListeners(){
        controllerList.forEach { jobVC ->
            jobVC.registerListener(viewModel)
            jobVC.jobDetailViewControllerList.forEach {
                it.registerListener(viewModel)
                it.orchardRowController.registerListener(viewModel)
            }
        }
    }

    private fun unregisterListeners(){
        controllerList.forEach { jobVC ->
            jobVC.unregisterListener(viewModel)
            jobVC.jobDetailViewControllerList.forEach {
                it.unregisterListener(viewModel)
                it.orchardRowController.unregisterListener(viewModel)
            }
        }
    }

    companion object{
        fun start(context: Context){
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}