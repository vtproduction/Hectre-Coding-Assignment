package com.midsummer.orchardjob.screens.common.rvcells

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.midsummer.orchardjob.Constants.TAG
import com.midsummer.orchardjob.screens.mainScreen.RVCellViewData
import com.midsummer.orchardjob.screens.mainScreen.RVViewData

/**
 * Created by nienle on 22,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */


class RVDiffCallback : DiffUtil.ItemCallback<RVCellViewData>() {
    companion object {
        const val ARG_BUTTON_STATE = "ARG_BUTTON_STATE"
        const val ARG_CURRENT_PRICE_RATE = "ARG_CURRENT_PRICE_RATE"
        const val ARG_SELECTED_ROW = "ARG_SELECTED_ROW"
    }

    override fun areItemsTheSame(oldItem: RVCellViewData, newItem: RVCellViewData): Boolean {
        val r =   oldItem.type == newItem.type
                && oldItem.jobType == newItem.jobType
                && oldItem.viewData?.job?.id == newItem.viewData?.job?.id
        return r
    }

    override fun areContentsTheSame(oldItem: RVCellViewData, newItem: RVCellViewData): Boolean {
        /*val tmp1 = oldItem.viewData
        val tmp2 = newItem.viewData
        Log.d(TAG, "areContentsTheSame 1: $tmp1")
        Log.d(TAG, "areContentsTheSame 2: $tmp2")
        val r1 = tmp1?.currentPriceRate == tmp2?.currentPriceRate
        val r2 = tmp1?.currentSelectedButton == tmp2?.currentSelectedButton
        val r3 = tmp1?.selectedRows == tmp2?.selectedRows


        val r = tmp1?.currentPriceRate == tmp2?.currentPriceRate
                && tmp1?.currentSelectedButton == tmp2?.currentSelectedButton
                && tmp2?.selectedRows?.let { tmp1?.selectedRows?.containsAll(it) } == true
        Log.d(TAG, "areContentsTheSame: $r1 $r2 $r3")*/
        return false
    }

    override fun getChangePayload(oldItem: RVCellViewData, newItem: RVCellViewData): Any? {
        if (oldItem.type == newItem.type
            && oldItem.jobType == newItem.jobType
            && oldItem.viewData?.job?.id == newItem.viewData?.job?.id){
                var hasChanged = false
                val diff = Bundle()
                if (oldItem.viewData?.currentSelectedButton != newItem.viewData?.currentSelectedButton){
                    newItem.viewData?.currentSelectedButton?.let { diff.putInt(ARG_BUTTON_STATE, it) }
                    hasChanged = true
                }
                if (oldItem.viewData?.currentPriceRate != newItem.viewData?.currentPriceRate){
                    newItem.viewData?.currentPriceRate?.let { diff.putInt(ARG_CURRENT_PRICE_RATE, it) }
                    hasChanged = true
                }
                if (oldItem.viewData?.selectedRows != newItem.viewData?.selectedRows){
                    newItem.viewData?.selectedRows?.let {
                        diff.putIntArray(ARG_CURRENT_PRICE_RATE, it.toIntArray())
                    }
                    hasChanged = true
                }

            if (hasChanged)
                return diff
        }

        return super.getChangePayload(oldItem, newItem)
    }


}
