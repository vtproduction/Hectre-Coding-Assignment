package com.midsummer.orchardjob.screens.common.dialogs

import androidx.fragment.app.FragmentManager
import javax.inject.Inject

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
class DialogNavigator @Inject constructor(
    private val fragmentManager: FragmentManager) {

    fun showServerErrorDialog() {
        fragmentManager.beginTransaction()
            .add(ServerErrorDialog.newInstance(), null)
            .commitAllowingStateLoss()
    }

    fun showGeneralErrorDialog(errMsg: String) {
        fragmentManager.beginTransaction()
            .add(GeneralErrorDialog.newInstance(errMsg), null)
            .commitAllowingStateLoss()
    }

    fun showGeneralErrorDialog(errMsgRes: Int) {
        fragmentManager.beginTransaction()
            .add(GeneralErrorDialog.newInstance(errMsgRes), null)
            .commitAllowingStateLoss()
    }

    fun showGeneralConfirmErrorDialog(msg: String, callback: GeneralConfirmDialog.Callback? = null) {
        fragmentManager.beginTransaction()
            .add(GeneralConfirmDialog.newInstance(msg, callback), null)
            .commitAllowingStateLoss()
    }

    fun showGeneralConfirmDialog(msgRes: Int, callback: GeneralConfirmDialog.Callback? = null) {
        fragmentManager.beginTransaction()
            .add(GeneralConfirmDialog.newInstance(msgRes, callback), null)
            .commitAllowingStateLoss()
    }
}