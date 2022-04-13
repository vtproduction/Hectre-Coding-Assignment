package com.midsummer.orchardjob.screens.common.jobs

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.Constants.TAG
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.pojo.Staff
import com.midsummer.orchardjob.screens.common.viewController.BaseViewController
import com.midsummer.orchardjob.screens.common.viewController.ViewControllerFactory
import org.w3c.dom.Text

/**
 * Created by nienle on 11,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class JobDetailViewController(
    layoutInflater: LayoutInflater,
    parent: ViewGroup?
    ) :  BaseViewController<JobDetailViewController.Listener>(
        layoutInflater,
        parent,
        R.layout.item_job
) {

    private var job: OrchardJob? = null
    lateinit var orchardRowController : OrchardRowController


    interface Listener {
        fun onApplyToAllButtonClicked(rate: String, jobType: Int?)
    }


    private var btnPriceRate : Button
    private var btnWages : Button
    private var txtWagesLabel : TextView
    private var containerRateEdit: View
    private var btnApplyToAll: Button
    private var edtRateEdit: EditText

    init {
        Log.d(TAG, "INIT JOB DETAIL: ")
        btnPriceRate = findViewById(R.id.btnPriceRate)
        btnWages = findViewById(R.id.btnPriceWage)
        txtWagesLabel = findViewById(R.id.txtWagesLabel)
        containerRateEdit = findViewById(R.id.item_rate_edit)
        btnApplyToAll = containerRateEdit.findViewById(R.id.btnApplyToAll)
        edtRateEdit = containerRateEdit.findViewById(R.id.edtRateEdit)

        btnPriceRate.setOnClickListener { onPriceRateBtnPressed() }
        btnWages.setOnClickListener { onWagesBtnPressed() }
        btnApplyToAll.setOnClickListener { onApplyToAllBtnClicked() }

        btnApplyToAll.isClickable = false
        edtRateEdit.addTextChangedListener {
                val text = it.toString()
                val isValidRate = text.toDoubleOrNull() != null
                btnApplyToAll.isClickable = isValidRate
                btnApplyToAll.setTextColor(ContextCompat.getColorStateList(context, if (isValidRate) R.color.main else R.color.brown))
            }

    }

    fun applyRate(rate: String?){
        if (rate != null) {
            val edt = containerRateEdit.findViewById<EditText>(R.id.edtRateEdit)
            edt.setText(rate)
            edt.setSelection(rate.length)
        }
    }

    fun toggleBottomDivider(show: Boolean){
        findViewById<View>(R.id.bottomDivider).visibility = if (show) View.VISIBLE else View.GONE
    }

    fun updateJob(job: OrchardJob){
        this.job = job
        this.orchardRowController.bindJob(job)
    }

    fun bindJob(job: OrchardJob, orchardRowController: OrchardRowController){
        this.job = job
        this.orchardRowController = orchardRowController

        rootView.findViewById<LinearLayout>(R.id.containerRow)
            .addView(orchardRowController.rootView)

        findViewById<TextView>(R.id.txtUserAva).text = getShortName(job)
        findViewById<TextView>(R.id.txtUserName).text = getFullName(job)
        findViewById<TextView>(R.id.txtOrchard).text = job.orchard
        findViewById<TextView>(R.id.txtBlock).text = job.block
        setupAvaColor(job.staff)

        txtWagesLabel.text = String.format(context.getString(R.string.job_name_will_be_paid),
            when (job.type) {
                Constants.JOB_TYPE_THINNING -> context.getString(R.string.thinning)
                Constants.JOB_TYPE_PRUNING -> context.getString(R.string.pruning)
                else -> context.getString(R.string.unknown_job_type)
            }
        )
    }

    private inline fun getShortName(job: OrchardJob) = "${job.staff.firstName.first()}${job.staff.lastName.first()}"
    private inline fun getFullName(job: OrchardJob) = "${job.staff.firstName} ${job.staff.lastName}"

    private fun setupAvaColor(staff: Staff){
        val code = staff.firstName.first().code + staff.lastName.first().code
        val bg = when(code % 4){
            0 -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_cyan) }
            1 -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_green) }
            2 -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_indigo) }
            3 -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_purple) }
            else -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_cyan) }
        }
        findViewById<TextView>(R.id.txtUserAva).background = bg
    }

    private fun onApplyToAllBtnClicked() {
        listeners.forEach {
            it.onApplyToAllButtonClicked(edtRateEdit.text.toString(), job?.type)
        }
    }

    private fun onPriceRateBtnPressed(){
        btnWages.backgroundTintList = ContextCompat.getColorStateList(context, R.color.brown)
        btnPriceRate.backgroundTintList = ContextCompat.getColorStateList(context, R.color.orange)

        btnWages.setTextColor(ContextCompat.getColorStateList(context, R.color.black))
        btnPriceRate.setTextColor(ContextCompat.getColorStateList(context, R.color.white))

        txtWagesLabel.visibility = View.GONE
        containerRateEdit.visibility = View.VISIBLE
    }

    private fun onWagesBtnPressed(){
        btnWages.backgroundTintList = ContextCompat.getColorStateList(context, R.color.orange)
        btnPriceRate.backgroundTintList = ContextCompat.getColorStateList(context, R.color.brown)

        btnWages.setTextColor(ContextCompat.getColorStateList(context, R.color.white))
        btnPriceRate.setTextColor(ContextCompat.getColorStateList(context, R.color.black))

        txtWagesLabel.visibility = View.VISIBLE
        containerRateEdit.visibility = View.GONE
    }
}