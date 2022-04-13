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
class GeneralConfirmDialog(private val msg: String = "", private val msgRes: Int = 0) : BaseDialog() {

    interface Callback{
        fun onConfirm()
        fun onDismiss()
    }

    private var callback: Callback? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val msg = msg.ifBlank { activity?.getString(msgRes) }

        return AlertDialog.Builder(activity).let {
            it.setTitle(getString(R.string.confirm))
            it.setMessage(msg)
            it.setCancelable(false)
            it.setPositiveButton(R.string.confirm) { _, _ ->
                dismiss()
                callback?.onConfirm()
            }
            it.setNegativeButton(R.string.cancel) {_, _ ->
                dismiss()
                callback?.onDismiss()
            }
            it.create()
        }
    }

    companion object {
        fun newInstance(msg: String, callback: Callback? = null): GeneralConfirmDialog {
            val d = GeneralConfirmDialog(msg = msg)
            d.callback = callback
            return d
        }

        fun newInstance(msgRes: Int, callback: Callback? = null): GeneralConfirmDialog {
            val d =  GeneralConfirmDialog(msgRes = msgRes)
            d.callback = callback
            return d
        }
    }
}