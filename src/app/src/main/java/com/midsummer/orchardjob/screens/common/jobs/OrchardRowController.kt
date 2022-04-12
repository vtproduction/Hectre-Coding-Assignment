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
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.textfield.TextInputLayout
import com.midsummer.orchardjob.Constants.TAG
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.pojo.Row
import com.midsummer.orchardjob.screens.common.viewController.BaseViewController
import java.text.FieldPosition
import kotlin.math.log

/**
 * Created by nienle on 11,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class OrchardRowController (
    private val layoutInflater: LayoutInflater,
    private val parent: ViewGroup?
) :  BaseViewController<OrchardRowController.Listener>(
    layoutInflater,
    parent,
    R.layout.item_orchard_row
){

    interface Listener {
        fun updateSelectedRows(type: Int, jobId: Int, selectedRows: List<Int>)
    }


    private lateinit var job: OrchardJob
    private val rows : MutableList<Row> = arrayListOf()
    private val buttons : MutableList<View> = arrayListOf()
    private val editLayouts: MutableList<View> = arrayListOf()
    private val selectedRows : MutableList<Int> = arrayListOf()

    private var rowButtonContainer : FlexboxLayout
    private var rowEditContainer: LinearLayout

    init {
        Log.d(TAG, "INIT ORCHARD ROW: ")
        rowButtonContainer = findViewById(R.id.containerRow)
        rowEditContainer = findViewById(R.id.containerRowEdit)
    }


    fun bindJob(job: OrchardJob){
        this.job = job
        this.rows.clear()

        this.rows.addAll(job.row)
        bindButtonLayouts()
        bindEditLayouts()
        Log.d(TAG, "bindJob: ${job.id}" +
                " - ${job.row.joinToString(separator = " - ", transform = { r -> "${r.rowId}:${r.current}" })}")

    }

    private fun bindButtonLayout(row: Row, index: Int){

        val buttonLayout = layoutInflater.inflate(R.layout.item_row_btn, parent, false)

        val button = buttonLayout.findViewById<Button>(R.id.btnRow)
        val orangeDot = buttonLayout.findViewById<View>(R.id.orangeDot)
        orangeDot.visibility = if (row.completed != null) View.VISIBLE else View.GONE
        button.text = "${row.rowId}"
        button.tag = false //false presents inactive, true present active
        buttons.add(buttonLayout)
        button.setOnClickListener { onRowButtonClicked(index, button.tag as Boolean) }
        rowButtonContainer.addView(buttonLayout)
    }
    private fun bindButtonLayouts(){
        Log.d(TAG, "bindButtonLayouts: ${rowButtonContainer.childCount} ${rows.size}")
        if (rowButtonContainer.childCount == 0){
            rows.forEachIndexed { index, row ->  bindButtonLayout(row, index)}
        }
    }

    private fun bindEditLayouts(){
        Log.d(TAG, "bindEditLayouts: ${rowEditContainer.childCount}")
        if (rowEditContainer.childCount == 0){
            rows.forEachIndexed { index, row ->  bindEditLayout(row, index)}
        }else{
            rows.forEachIndexed { index, row ->
                rowEditContainer.getChildAt(index)
                    .findViewById<EditText>(R.id.editTextNumberPerRow)
                    .setText("${row.current}")
            }
        }

    }

    private fun bindEditLayout(row: Row, index: Int) {
        Log.d(TAG, "bindEditLayout: ${row.rowId}:${row.current}")
        val editLayout = layoutInflater.inflate(R.layout.item_tree_for_row, parent, false)
        val label1 = editLayout.findViewById<TextView>(R.id.label1)
        val label2 = editLayout.findViewById<TextView>(R.id.label2)
        val inputLayout = editLayout.findViewById<TextInputLayout>(R.id.inputLayout)
        val editText = editLayout.findViewById<EditText>(R.id.editTextNumberPerRow)

        label1.text = String.format(context.getString(R.string.trees_for_row), row.rowId)
        editText.setText("${row.current}")

        editText.addTextChangedListener {
            val treeNumber = it.toString().toInt()
            if (treeNumber > getAvailableTree(row)){
                editText.setText("${getAvailableTree(row)}")
                editText.setSelection(editText.text.length)
            }
        }

        if (row.completed != null){
            label2.visibility = View.VISIBLE
            label2.text = String.format("%s %s (%d)", row.completed.staff.firstName,
                row.completed.staff.lastName, row.completed.completed)
        }else{
            label2.visibility = View.INVISIBLE
        }

        inputLayout.suffixText = "/${getAvailableTree(row)}"
        editLayout.visibility = View.GONE
        editLayouts.add(editLayout)
        rowEditContainer.addView(editLayout)

    }

    private fun getAvailableTree(row: Row) : Int {
        return if (row.completed != null){
            row.totalTrees - row.completed.completed
        }else{
            row.totalTrees
        }
    }


    private fun onRowButtonClicked(position: Int, isEnabled: Boolean){
        val buttonLayout = buttons[position]
        val editLayout = editLayouts[position]

        val button = buttonLayout.findViewById<Button>(R.id.btnRow)

        if (isEnabled){
            button.backgroundTintList = ContextCompat.getColorStateList(context, R.color.brown)
            button.setTextColor(ContextCompat.getColorStateList(context, R.color.black))
        }else{
            button.backgroundTintList = ContextCompat.getColorStateList(context, R.color.blue)
            button.setTextColor(ContextCompat.getColorStateList(context, R.color.white))
        }

        button.tag = !isEnabled
        editLayout.visibility = if (isEnabled) View.GONE else View.VISIBLE

        if (isEnabled) selectedRows.remove(rows[position].rowId)
        else selectedRows.add(rows[position].rowId)
        listeners.forEach {
            it.updateSelectedRows(job.type, job.id, selectedRows)
        }
    }

}