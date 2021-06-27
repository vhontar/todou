package com.easycoding.todou.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.easycoding.todou.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodouActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todou)
    }
}