package com.easycodingstudio.todou.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
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
        viewDataBinding.apply {
//            ivMenu.setOnClickListener {
//                if (drawer.isDrawerOpen(GravityCompat.START)) {
//                    drawer.closeDrawer(GravityCompat.START)
//                } else {
//                    drawer.openDrawer(GravityCompat.START)
//                }
//            }
        }

        // setup action bar for consecutive fragments to add a navigating back button
        // have no idea why it's not working that way
        // navController = findNavController(viewDataBinding.navHostFragmentContainer.id)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        // hide action bar
        supportActionBar?.hide()

        // turn on dark mode
        setDefaultNightMode(MODE_NIGHT_YES)
//        viewModel.settingsDarkMode.observe(this) {
//            if (it) setDefaultNightMode(MODE_NIGHT_YES)
//            else setDefaultNightMode(MODE_NIGHT_NO)
//            finish()
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//            startActivity(intent)
//            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
//        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp() || super.onSupportNavigateUp()
}