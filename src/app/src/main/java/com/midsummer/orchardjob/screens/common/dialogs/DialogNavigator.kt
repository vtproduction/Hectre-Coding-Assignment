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
}