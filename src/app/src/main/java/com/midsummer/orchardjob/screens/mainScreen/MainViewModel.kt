package com.midsummer.orchardjob.screens.mainScreen

import android.util.Log
import androidx.lifecycle.*
import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.Constants.JOB_TYPE_PRUNING
import com.midsummer.orchardjob.Constants.JOB_TYPE_THINNING
import com.midsummer.orchardjob.Constants.TAG
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.data.OrchardJobRepository
import com.midsummer.orchardjob.pojo.FieldConfig
import com.midsummer.orchardjob.pojo.OrchardJob
import com.midsummer.orchardjob.screens.common.dialogs.DialogNavigator
import com.midsummer.orchardjob.screens.common.dialogs.GeneralConfirmDialog
import com.midsummer.orchardjob.screens.common.jobs.JobDetailViewController
import com.midsummer.orchardjob.screens.common.jobs.JobsViewController
import com.midsummer.orchardjob.screens.common.jobs.OrchardRowController
import com.midsummer.orchardjob.screens.common.rvcells.RVAdapter
import com.midsummer.orchardjob.screens.common.viewModel.SavedStateViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max

/**
 * Created by nienle on 10,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class MainViewModel @Inject constructor(
    private val repository: OrchardJobRepository,
    private val dialogNavigator: DialogNavigator
) : SavedStateViewModel(),
    RVAdapter.CellViewListener{

    private lateinit var _pruningJobs: MutableLiveData<List<OrchardJob>>
    val pruningJobs : LiveData<List<OrchardJob>> get() = _pruningJobs

    private lateinit var _thinningJobs: MutableLiveData<List<OrchardJob>>
    val thinningJobs : LiveData<List<OrchardJob>> get() = _thinningJobs

    private lateinit var _rateForAllPruning: MutableLiveData<String>
    val rateForAllPruning : LiveData<String> get() = _rateForAllPruning

    private lateinit var _rateForAllThinning: MutableLiveData<String>
    val rateForAllThinning : LiveData<String> get() = _rateForAllThinning

    private lateinit var _fieldConfigs: MutableLiveData<List<FieldConfig>>
    val fieldConfigs : LiveData<List<FieldConfig>> get() = _fieldConfigs

    private val _viewData: MediatorLiveData<List<RVCellViewData>> = MediatorLiveData()
    val viewData : LiveData<List<RVCellViewData>> get() = _viewData




    private val currentSelectedRows : MutableList<Triple<Int, Int, List<Int>>> = arrayListOf()


    private lateinit var handler: SavedStateHandle

    override fun init(handler: SavedStateHandle) {
        this.handler = handler
        _pruningJobs = handler.getLiveData("pruning")
        _thinningJobs = handler.getLiveData("thinning")
        _fieldConfigs = handler.getLiveData("fieldConfig")
        _rateForAllThinning = handler.getLiveData("rateThinning")
        _rateForAllPruning = handler.getLiveData("ratePruning")
        _viewData.addSource(pruningJobs){
            val tmp = viewData.value?.toMutableList() ?: arrayListOf<RVCellViewData>().toMutableList()
            if (tmp.isEmpty()){
                Log.d(TAG, "init pruning empty:")
                tmp.add(RVCellViewData.createHeader(Constants.JOB_TYPE_PRUNING))
                it.forEach { job -> tmp.add(RVCellViewData.createItem(RVViewData.createRVViewData(job))) }
                tmp.add(RVCellViewData.createFooter(JOB_TYPE_PRUNING))
            }else {
                val s = tmp.indexOf(RVCellViewData.createHeader(Constants.JOB_TYPE_PRUNING)) + 1
                val e = tmp.indexOf(RVCellViewData.createFooter(JOB_TYPE_PRUNING)) - 1
                Log.d(TAG, "init pruning: $s $e")
                removeSlice(tmp, s, e)
                it.reversed().forEach { job -> tmp.add(s,RVCellViewData.createItem(RVViewData.createRVViewData(job))) }
            }
            _viewData.value = tmp
        }
        _viewData.addSource(thinningJobs){
            val tmp = viewData.value?.toMutableList() ?: arrayListOf<RVCellViewData>().toMutableList()
            Log.d(TAG, "init  ${pruningJobs.value?.size?.plus(2)}")
            if (tmp.isEmpty() || tmp.size == pruningJobs.value?.size?.plus(2)){
                Log.d(TAG, "init thinning empty:")
                tmp.add(RVCellViewData.createHeader(JOB_TYPE_THINNING))
                it.forEach { job -> tmp.add(RVCellViewData.createItem(RVViewData.createRVViewData(job))) }
                tmp.add(RVCellViewData.createFooter(JOB_TYPE_THINNING))
            }else {
                val s = tmp.indexOf(RVCellViewData.createHeader(JOB_TYPE_THINNING)) + 1
                val e = tmp.indexOf(RVCellViewData.createFooter(JOB_TYPE_THINNING)) - 1
                Log.d(TAG, "init thinning: $s $e ${tmp.size} ${pruningJobs.value?.size}")
                removeSlice(tmp, s, e)
                it.reversed().forEach { job -> tmp.add(s,RVCellViewData.createItem(RVViewData.createRVViewData(job))) }
            }
            _viewData.value = tmp
        }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {

            val fieldConfig = repository.fetchLocalFieldConfigs()
            _fieldConfigs.value = fieldConfig

            val data1 = repository.fetchLocalPruningJobs()

            data1.forEach { job ->
                job.row.forEach { r ->
                    r.totalTrees = fieldConfig.find { f -> f.rowId == r.rowId }?.totalTrees ?: 0
                }
            }

            _pruningJobs.value = data1
            val data2 = repository.fetchLocalThinningJobs()

            data2.forEach { job ->
                job.row.forEach { r ->
                    r.totalTrees = fieldConfig.find { f -> f.rowId == r.rowId }?.totalTrees ?: 0
                }
            }
            _thinningJobs.value = data2

        }
    }

    fun saveData(){
        handler["pruning"] = _pruningJobs.value
        handler["thinning"] = _thinningJobs.value
        handler["rateThinning"] = _rateForAllThinning.value
        handler["ratePruning"] = _rateForAllPruning.value
        handler["fieldConfig"] = _fieldConfigs.value
    }


    /*override fun onApplyToAllButtonClicked(rate: String, jobType: Int?) {
        when(jobType){
            Constants.JOB_TYPE_PRUNING -> _rateForAllPruning.value = rate
            Constants.JOB_TYPE_THINNING -> _rateForAllThinning.value = rate
        }
    }

    override fun onMaxTreeBtnClicked(jobType: Int) {
        val currentRows = currentSelectedRows.filter { r -> r.first == jobType }


        if (currentRows.isEmpty()){
            dialogNavigator.showGeneralErrorDialog(R.string.err_mgs_no_row_selected)
            return
        }

        var tmp1 : MutableList<Int> = arrayListOf()
        currentRows.forEach { 
            tmp1 = tmp1.union(it.third).toMutableList()
        }

        val jobList = if (jobType == Constants.JOB_TYPE_PRUNING) pruningJobs.value else thinningJobs.value
        tmp1.forEach { rowId ->
            val maxTrees = fieldConfigs.value?.find { f -> f.rowId == rowId }?.totalTrees ?: return
            val tmp2 = currentRows.filter { i -> i.third.contains(rowId) }
            var availableTrees = maxTrees
            tmp2.forEach { item ->
                val completed = jobList?.find { job -> job.id == item.second }
                    ?.row?.find { row -> row.rowId == rowId }?.completed?.completed ?: 0
                availableTrees -= completed
            }

            val treeForEach = availableTrees / tmp2.size
            val lastTrees = availableTrees - treeForEach * (tmp2.size - 1)
            tmp2.forEachIndexed { index, item ->
                val t = if (index < tmp2.size - 1) treeForEach else lastTrees
                jobList?.find { job -> job.id == item.second }
                    ?.row?.find { row -> row.rowId == rowId }?.current = t
            }
        }

        jobList?.forEach { job ->
            Log.d(
                TAG,
                "onMaxTreeBtnClicked: ${job.id} " +
                        job.row.joinToString(separator = " - ", transform = { r -> "${r.rowId}:${r.current}" })
            )
        }

        if (jobType == JOB_TYPE_PRUNING) {
            _pruningJobs.value = jobList!!
        }

        if (jobType == JOB_TYPE_THINNING) {
            _thinningJobs.value = jobList!!
        }

    }


    override fun updateSelectedRows(type: Int, jobId: Int, selectedRows: List<Int>) {
        val current = currentSelectedRows.find { r -> r.first == type && r.second == jobId }

        if (current != null){
            currentSelectedRows.remove(current)
        }
        if (selectedRows.isNotEmpty())
            currentSelectedRows.add(Triple(type, jobId, selectedRows))
    }*/


    override fun onPriceRateBtnClicked(position: Int) {
        Log.d(TAG, "onPriceRateBtnClicked: $position")
        val tmp = _viewData.value?.toMutableList() ?: mutableListOf()
        val item = tmp[position]
        item.viewData?.currentSelectedButton = 1
        tmp[position] = item
        _viewData.value = tmp
    }

    override fun onWagesBtnClicked(position: Int) {
        val tmp = _viewData.value?.toMutableList() ?: mutableListOf()
        val item = tmp[position]
        item.viewData?.currentSelectedButton = 2
        tmp[position] = item
        _viewData.value = tmp
    }

    override fun onAddMaxTreeBtnClicked(type: Int) {
        Log.d(TAG, "onAddMaxTreeBtnClicked: $type ")

    }

    override fun onApplyToAllBtnClicked(position: Int, value: String) {
        Log.d(TAG, "onApplyToAllBtnClicked: $position $value")
        
        val tmp = _viewData.value?.toMutableList() ?: mutableListOf()
        val item = tmp[position].viewData ?: return
        val type = item.job.type

        val tmp2 = mutableListOf<RVCellViewData>()
        tmp.forEach { item ->
            if (item.viewData?.job?.type == type ){
                item.viewData?.currentPriceRate = value.toInt()
            }
            tmp2.add(item)
        }

        _viewData.value = tmp2
    }

    override fun onRowUpdated(position: Int, data: List<Int>) {
        val tmp = _viewData.value?.toMutableList() ?: mutableListOf()
        val item = tmp[position]
        item.viewData?.selectedRows?.clear()
        item.viewData?.selectedRows?.addAll(data)
        tmp[position] = item
        //_viewData.value = tmp
    }

    private fun <T> removeSlice(list: MutableList<T>, from: Int, end: Int) {
        for (i in end downTo from) {
            list.removeAt(i)
        }
    }
}