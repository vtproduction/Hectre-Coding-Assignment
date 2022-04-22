package com.midsummer.orchardjob.screens.mainScreen

import com.midsummer.orchardjob.Constants
import com.midsummer.orchardjob.pojo.OrchardJob

/**
 * Created by nienle on 21,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
data class RVCellViewData(
    var type: Int,
    var jobType: Int?,
    var viewData: RVViewData?
) {
    companion object {
        fun createHeader(jobType: Int?) : RVCellViewData {
            return RVCellViewData(Constants.CELL_TYPE_HEADER, jobType, null)
        }

        fun createFooter(jobType: Int?) : RVCellViewData {
            return RVCellViewData(Constants.CELL_TYPE_FOOTER, jobType, null)
        }

        fun createItem(viewData: RVViewData?) : RVCellViewData {
            return RVCellViewData(Constants.CELL_TYPE_CONTENT, null, viewData)
        }
    }
}


data class RVViewData(
    var job: OrchardJob,
    var currentSelectedButton: Int, //0: None, 1: Price rate, 2: Wages
    var currentPriceRate: Int,
    var selectedRows: MutableList<Int>
) {
    companion object {
        fun createRVViewData(job: OrchardJob) : RVViewData {
            return RVViewData(
                job,
                0,
                0, mutableListOf()
            )
        }
    }
}



