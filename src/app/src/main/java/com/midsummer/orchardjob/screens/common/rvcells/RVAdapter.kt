package com.midsummer.orchardjob.screens.common.rvcells

import android.content.Context
import android.os.Bundle
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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.textfield.TextInputLayout
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.Constants.TAG
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.pojo.Row
import com.midsummer.orchardjob.pojo.Staff
import com.midsummer.orchardjob.screens.common.jobs.OrchardRowController
import com.midsummer.orchardjob.screens.mainScreen.RVCellViewData
import com.nex3z.flowlayout.FlowLayout
import org.w3c.dom.Text

/**
 * Created by nienle on 21,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class RVAdapter(private val context: Context,
                private val callback: CellViewListener) :
    ListAdapter<RVCellViewData,RecyclerView.ViewHolder>(RVDiffCallback())  {


    interface CellViewListener {
        fun onAddMaxTreeBtnClicked(type: Int)
        fun onPriceRateBtnClicked(position: Int)
        fun onWagesBtnClicked(position: Int)
        fun onApplyToAllBtnClicked(position: Int, value: String)
        fun onRowUpdated(position: Int, data: List<Int>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            Constants.CELL_TYPE_HEADER -> {
                HeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item_header, parent, false))
            }
            Constants.CELL_TYPE_FOOTER -> {
                FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item_footer, parent, false))
            }
            else -> {
                ContentViewHolder(LayoutInflater.from(context).inflate(R.layout.rv_item_content, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ContentViewHolder -> {
                holder.bindData(getItem(position), position)
            }
            is HeaderViewHolder -> {
                holder.bindData(getItem(position))
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty() || payloads[0] !is Bundle){
            when(holder) {
                is ContentViewHolder -> {
                    holder.bindData(getItem(position), position)
                }
                is HeaderViewHolder -> {
                    holder.bindData(getItem(position))
                }
            }
        }

        else when(holder){
            is ContentViewHolder -> {
                holder.updateData(payloads[0] as Bundle)
            }
            is HeaderViewHolder -> {
                holder.bindData(getItem(position))
            }
        }
    }

    override fun getItemCount() = currentList.size

    override fun getItemViewType(position: Int) = getItem(position).type

    private inner class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var rowController: OrchardRowController? = null
        fun updateData(bundle: Bundle){

            if (bundle.containsKey(RVDiffCallback.ARG_BUTTON_STATE)){
                val selectedButton = bundle.getInt(RVDiffCallback.ARG_BUTTON_STATE)
                setButtonState(selectedButton)
            }
            if (bundle.containsKey(RVDiffCallback.ARG_CURRENT_PRICE_RATE)){
                val currentPriceRate = bundle.getInt(RVDiffCallback.ARG_CURRENT_PRICE_RATE)
                setPriceRate(currentPriceRate.toString())
            }

            if (bundle.containsKey(RVDiffCallback.ARG_SELECTED_ROW)){
                val selectedRows = bundle.getIntArray(RVDiffCallback.ARG_CURRENT_PRICE_RATE)
                updateRows(selectedRows)
            }
        }
        
        fun bindData(data: RVCellViewData, position: Int){
            if (data.viewData == null) return

            val job = data.viewData!!.job
            val selectedButton = data.viewData!!.currentSelectedButton
            val currentPriceRate = data.viewData!!.currentPriceRate

            itemView.findViewById<TextView>(R.id.txtUserAva).text = getShortName(job)
            itemView.findViewById<TextView>(R.id.txtUserName).text = getFullName(job)
            itemView.findViewById<TextView>(R.id.txtOrchard).text = job.orchard
            itemView.findViewById<TextView>(R.id.txtBlock).text = job.block

            setupAvaColor(job.staff)

            itemView.findViewById<TextView>(R.id.txtWagesLabel).text = String.format(context.getString(R.string.job_name_will_be_paid),
                when (job.type) {
                    Constants.JOB_TYPE_THINNING -> context.getString(R.string.thinning)
                    Constants.JOB_TYPE_PRUNING -> context.getString(R.string.pruning)
                    else -> context.getString(R.string.unknown_job_type)
                }
            )

            setButtonState(selectedButton)
            setPriceRate(currentPriceRate.toString())
            setRows(job, itemView.context, position, data.viewData!!.selectedRows.toIntArray())

            itemView.findViewById<Button>(R.id.btnPriceWage).setOnClickListener { callback.onWagesBtnClicked(position) }
            itemView.findViewById<Button>(R.id.btnPriceRate).setOnClickListener { callback.onPriceRateBtnClicked(position) }
            itemView.findViewById<Button>(R.id.btnApplyToAll).setOnClickListener { callback.onApplyToAllBtnClicked(position,
                itemView.findViewById<EditText>(R.id.edtRateEdit).text.toString()) }
        }

        private fun updateRows(selectedRows: IntArray?) {
            Log.d(TAG, "updateRows: ${selectedRows?.joinToString()}")
        }

        private fun setRows(job: OrchardJob, context: Context, position: Int, selectedRows: IntArray){
            rowController = OrchardRowController(LayoutInflater.from(context), null)
            rowController?.bindJob(job, selectedRows.toMutableList())
            val container = itemView.findViewById<LinearLayout>(R.id.containerRow)
            container.removeAllViews()
            container.addView(rowController?.rootView)

            rowController?.registerListener(object : OrchardRowController.Listener{
                override fun updateSelectedRows(type: Int, jobId: Int, selectedRows: List<Int>) {
                    callback.onRowUpdated(position, selectedRows)
                }
            })
        }

        private fun setPriceRate(value: String){
            val btnApplyToAll = itemView.findViewById<Button>(R.id.btnApplyToAll)
            val edtRateEdit = itemView.findViewById<EditText>(R.id.edtRateEdit)

            edtRateEdit.setText(value)
            edtRateEdit.setSelection(value.length)

            btnApplyToAll.isClickable = false
            edtRateEdit.addTextChangedListener {
                val text = it.toString()
                val isValidRate = text.toDoubleOrNull() != null
                btnApplyToAll.isClickable = isValidRate
                btnApplyToAll.setTextColor(ContextCompat.getColorStateList(context, if (isValidRate) R.color.main else R.color.brown))
            }
        }

        private fun setButtonState(selectedButton: Int){
            val btnWages = itemView.findViewById<Button>(R.id.btnPriceWage)
            val btnPriceRate = itemView.findViewById<Button>(R.id.btnPriceRate)
            val txtWagesLabel = itemView.findViewById<TextView>(R.id.txtWagesLabel)
            val containerRateEdit = itemView.findViewById<View>(R.id.item_rate_edit)

            when(selectedButton){
                0 -> {
                    btnWages.backgroundTintList = ContextCompat.getColorStateList(context, R.color.brown)
                    btnPriceRate.backgroundTintList = ContextCompat.getColorStateList(context, R.color.brown)
                    btnPriceRate.setTextColor(ContextCompat.getColorStateList(context, R.color.black))
                    btnWages.setTextColor(ContextCompat.getColorStateList(context, R.color.black))
                    txtWagesLabel.visibility = View.GONE
                    containerRateEdit.visibility = View.GONE
                }
                1 -> {
                    btnWages.backgroundTintList = ContextCompat.getColorStateList(context, R.color.brown)
                    btnPriceRate.backgroundTintList = ContextCompat.getColorStateList(context, R.color.orange)

                    btnWages.setTextColor(ContextCompat.getColorStateList(context, R.color.black))
                    btnPriceRate.setTextColor(ContextCompat.getColorStateList(context, R.color.white))

                    txtWagesLabel.visibility = View.GONE
                    containerRateEdit.visibility = View.VISIBLE
                }
                2 -> {
                    btnWages.backgroundTintList = ContextCompat.getColorStateList(context, R.color.orange)
                    btnPriceRate.backgroundTintList = ContextCompat.getColorStateList(context, R.color.brown)

                    btnWages.setTextColor(ContextCompat.getColorStateList(context, R.color.white))
                    btnPriceRate.setTextColor(ContextCompat.getColorStateList(context, R.color.black))

                    txtWagesLabel.visibility = View.VISIBLE
                    containerRateEdit.visibility = View.GONE
                }
            }
        }


        private fun setupAvaColor(staff: Staff){
            val code = staff.firstName.first().code + staff.lastName.first().code
            val bg = when(code % 4){
                0 -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_cyan) }
                1 -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_green) }
                2 -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_indigo) }
                3 -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_purple) }
                else -> { ContextCompat.getDrawable(context, R.drawable.bg_circle_cyan) }
            }
            itemView.findViewById<TextView>(R.id.txtUserAva).background = bg
        }

        private  fun getShortName(job: OrchardJob) = "${job.staff.firstName.first()}${job.staff.lastName.first()}"
        private  fun getFullName(job: OrchardJob) = "${job.staff.firstName} ${job.staff.lastName}"
    }


    private inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(data: RVCellViewData){
            itemView.findViewById<TextView>(R.id.header_title)
                .text = when(data.jobType) {
                    Constants.JOB_TYPE_PRUNING -> context.getString(R.string.pruning)
                    Constants.JOB_TYPE_THINNING -> context.getString(R.string.thinning)
                    else -> context.getString(R.string.unknown_job_type)
                }

            itemView.findViewById<Button>(R.id.btnAddMaxTree)
                .setOnClickListener { callback.onAddMaxTreeBtnClicked(data.jobType!!) }
        }
    }

    private inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}