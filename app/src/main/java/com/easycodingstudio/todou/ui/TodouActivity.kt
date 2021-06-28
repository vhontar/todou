package com.easycodingstudio.todou.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.ActivityTodouBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodouActivity : AppCompatActivity() {
    private lateinit var viewDataBinding: ActivityTodouBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_todou)

        // setup action bar for consecutive fragments to add a navigating back button
        navController = findNavController(viewDataBinding.navHostFragmentContainer.id)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp() || super.onSupportNavigateUp()
}