package com.atbelectonics.atbapp.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.atbelectonics.atbapp.App
import com.atbelectonics.atbapp.R
import com.atbelectonics.atbapp.databinding.ActivityHostBinding
import com.atbelectonics.atbapp.mdns.models.HostViewModel
import kotlinx.coroutines.launch

class HostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHostBinding
    private val viewModel: HostViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                //val wordsDao = (application as App).db.wordsDao()
                return HostViewModel() as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHostBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_host)

        //lateinit var viewModel: HostViewModel
        //viewModel = ViewModelProvider(this).get(HostViewModel::class.java)

        //setContentView(R.layout.main_activity)

        //binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.buttonReload.setOnClickListener {
            App.startNsdWorker()
        }

        lifecycleScope.launch {
        }

        viewModel.viewModelScope.launch {
        }
    }
}