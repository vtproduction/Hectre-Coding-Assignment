package com.midsummer.orchardjob.screens.common.dialogs

import androidx.fragment.app.DialogFragment
import com.midsummer.orchardjob.di.presentation.PresentationModule
import com.midsummer.orchardjob.screens.common.activities.BaseActivity

/**
 * Created by nienle on 09,April,2022
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
open class BaseDialog : DialogFragment() {


    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.newPresentationComponent(
            PresentationModule(this)
        )
    }

    val injector get() = presentationComponent
}