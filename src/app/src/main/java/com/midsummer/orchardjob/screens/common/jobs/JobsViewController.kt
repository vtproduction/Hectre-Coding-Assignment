package com.midsummer.orchardjob.screens.common.jobs

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.Constants.TAG
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.screens.common.viewController.BaseViewController
import com.midsummer.orchardjob.screens.common.viewController.ViewControllerFactory
import javax.inject.Inject

/**
 * Created by nienle on 11,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class JobsViewController(
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?
) : BaseViewController<JobsViewController.Listener>(
    layoutInflater,
    parent,
    R.layout.item_job_container
){

    interface Listener {
        fun onMaxTreeBtnClicked(jobType: Int)
    }

    val jobDetailViewControllerList : MutableList<JobDetailViewController> = arrayListOf()
    private var titleTextView: TextView
    private var btnAddMaxTree: Button
    private var container: LinearLayout
    private var jobType: Int = 0

    init {
        Log.d(TAG, "INIT JOBS VIEW: ")
        titleTextView = findViewById(R.id.header_title)
        btnAddMaxTree = findViewById(R.id.btnAddMaxTree)
        container = findViewById(R.id.container)

        btnAddMaxTree.setOnClickListener {
            for (listener in listeners) listener.onMaxTreeBtnClicked(jobType)
        }
    }


    fun applyRateToAllStaff(rate: String?){
        jobDetailViewControllerList.forEach {
            it.applyRate(rate)
        }
    }

    fun bindJobs(jobs: List<OrchardJob>, type: Int) {
        jobType = type
        when (type){
            Constants.JOB_TYPE_PRUNING -> titleTextView.text = parent?.context?.getString(R.string.pruning)
            Constants.JOB_TYPE_THINNING -> titleTextView.text = parent?.context?.getString(R.string.thinning)
            else -> titleTextView.text = parent?.context?.getString(R.string.unknown_job_type)
        }
    }

    fun bindJobDetails(
        jobDetailViewControllers: List<JobDetailViewController>,
        jobs: List<OrchardJob>
    ) {
        Log.d(TAG, "bindJobDetails: ${jobDetailViewControllerList.size}")
        if (jobDetailViewControllerList.isEmpty()){
            jobDetailViewControllers.forEachIndexed { index, jobDetailViewController ->
                jobDetailViewControllerList.add(jobDetailViewController)
                container.addView(jobDetailViewController.rootView)
                if (index == jobDetailViewControllers.size - 1){
                    jobDetailViewController.toggleBottomDivider(false)
                }
            }
        }else{
            jobDetailViewControllerList.forEachIndexed { index, jobDetailViewController ->
                jobDetailViewController.updateJob(jobs[index])
            }
        }

    }



}