package com.easycodingstudio.todou.helper

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.easycodingstudio.todou.R

class DrawerHelper(
    private val activity: Activity,
    private val drawer: DrawerLayout,
    private val home: View
) {
    private val actionBarDrawerToggle: ActionBarDrawerToggle =
        object : ActionBarDrawerToggle(activity, drawer, R.string.search_title_menu, R.string.filter_title_menu) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                home.translationX = slideX
            }
        }

    init {
        drawer.setScrimColor(Color.TRANSPARENT)
        drawer.addDrawerListener(actionBarDrawerToggle)
    }

    fun onMenuClicked() {
        if (drawer.isDrawerOpen(Gravity.START)) close()
        else open()
    }

    fun open() {
        drawer.openDrawer(Gravity.START, true)
    }
    fun close() {
        drawer.closeDrawer(Gravity.START, true)
    }
}