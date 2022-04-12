package com.midsummer.orchardjob.screens.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import com.midsummer.orchardjob.R

/**
 * Created by nienle on 12,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class GeneralErrorDialog(private val errMsg: String = "", private val errMsgRes: Int = 0) : BaseDialog() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val msg = errMsg.ifBlank { activity?.getString(errMsgRes) }

        return AlertDialog.Builder(activity).let {
            it.setTitle(getString(R.string.error))
            it.setMessage(msg)
            it.setCancelable(false)
            it.setPositiveButton(R.string.got_it) { _, _ -> dismiss() }
            it.create()
        }
    }

    companion object {
        fun newInstance(errMsg: String): GeneralErrorDialog {
            return GeneralErrorDialog(errMsg = errMsg)
        }

        fun newInstance(errMsgRes: Int): GeneralErrorDialog {
            return GeneralErrorDialog(errMsgRes = errMsgRes)
        }
    }
}