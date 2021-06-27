package com.easycoding.todou.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.easycoding.todou.R
import com.easycoding.todou.databinding.ActivityTodouBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TodouActivity : AppCompatActivity() {
    private lateinit var viewDataBinding: ActivityTodouBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_todou)
    }
}