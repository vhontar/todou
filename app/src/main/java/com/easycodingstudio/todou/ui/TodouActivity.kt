package com.easycodingstudio.todou.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
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
        // have no idea why it's not working that way
        // navController = findNavController(viewDataBinding.navHostFragmentContainer.id)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        // TODO for testing purpose
        setDefaultNightMode(MODE_NIGHT_NO)
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp() || super.onSupportNavigateUp()
}