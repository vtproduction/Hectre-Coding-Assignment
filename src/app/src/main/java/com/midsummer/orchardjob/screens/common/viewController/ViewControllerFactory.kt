package com.midsummer.orchardjob.screens.common.viewController

import android.view.LayoutInflater
import android.view.ViewGroup
import com.midsummer.orchardjob.screens.common.jobs.JobDetailViewController
import com.midsummer.orchardjob.screens.common.jobs.JobsViewController
import com.midsummer.orchardjob.screens.common.jobs.OrchardRowController
import javax.inject.Inject

/**
 * Created by nienle on 11,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class ViewControllerFactory @Inject constructor(
    private val layoutInflater: LayoutInflater
){
    fun newJobsViewController(parent: ViewGroup?) : JobsViewController {
        return JobsViewController(layoutInflater, parent)
    }

    fun newJobDetailController(parent: ViewGroup?) : JobDetailViewController {
        return JobDetailViewController(layoutInflater, parent)
    }
    fun newOrchardRowController(parent: ViewGroup?) : OrchardRowController {
        return OrchardRowController(layoutInflater, parent)
    }
}