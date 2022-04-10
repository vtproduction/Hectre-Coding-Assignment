
package com.midsummer.orchardjob.screens.loadingScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.midsummer.orchardjob.R
import com.midsummer.orchardjob.databinding.ActivityLoadingBinding
import com.midsummer.orchardjob.screens.common.ScreenNavigator
import com.midsummer.orchardjob.screens.common.activities.BaseActivity
import com.midsummer.orchardjob.screens.common.dialogs.DialogNavigator
import com.midsummer.orchardjob.screens.common.viewModel.ViewModelFactory
import javax.inject.Inject

class LoadingActivity : BaseActivity() {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var screenNavigator: ScreenNavigator
    private lateinit var viewModel: LoadingViewModel

    private lateinit var binding : ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        injector.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoadingViewModel::class.java]

        observeLiveData()
        bindListener()
    }



    private fun observeLiveData() {
        viewModel.isNotFound.observe(this){
            binding.notFoundLabel.visibility = if (it) View.VISIBLE else View.GONE
            binding.retryButton.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) {
            binding.loadingIndicator.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.jobs.observe(this) { jobs ->
            if (!jobs.isNullOrEmpty()){
                screenNavigator.goToMain()
                finish()
            }
        }
    }

    private fun bindListener(){

        binding.retryButton.setOnClickListener {
            viewModel.loadData()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveData()
        super.onSaveInstanceState(outState)
    }
}