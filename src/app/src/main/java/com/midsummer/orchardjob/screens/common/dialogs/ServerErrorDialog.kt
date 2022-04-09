package com.midsummer.orchardjob.screens.common.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class ServerErrorDialog : BaseDialog() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return AlertDialog.Builder(activity).let {
            it.setTitle("title")
            it.setMessage("msg")
            it.setCancelable(false)
            it.setPositiveButton("dialog btn") { _, _ -> dismiss() }
            it.create()
        }
    }

    companion object {
        fun newInstance(): ServerErrorDialog {
            return ServerErrorDialog()
        }
    }
}