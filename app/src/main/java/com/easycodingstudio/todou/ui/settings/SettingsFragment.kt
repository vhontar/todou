package com.easycodingstudio.todou.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.easycodingstudio.todou.R
import com.easycodingstudio.todou.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var viewDataBinding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding = FragmentSettingsBinding.bind(view)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        viewDataBinding.apply {
            rlDarkMode.setOnClickListener {
                viewModel.updateDarkModeSettings(!swcDarkMode.isChecked)

                if (!swcDarkMode.isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            rlFilterTodosByDate.setOnClickListener {
                viewModel.updateFilterTodosByDateSettings(!swcFilterTodosByDate.isChecked)
                if (swcFilterTodosByName.isChecked)
                    viewModel.updateFilterTodosByNameSettings(false)
                else if (swcFilterTodosByDate.isChecked && !swcFilterTodosByName.isChecked)
                    viewModel.updateFilterTodosByNameSettings(true)
            }
            rlFilterTodosByName.setOnClickListener {
                viewModel.updateFilterTodosByNameSettings(!swcFilterTodosByName.isChecked)
                if (swcFilterTodosByDate.isChecked)
                    viewModel.updateFilterTodosByDateSettings(false)
                else if (swcFilterTodosByName.isChecked && !swcFilterTodosByDate.isChecked)
                    viewModel.updateFilterTodosByDateSettings(true)
            }
            rlHideCompletedTodos.setOnClickListener {
                viewModel.updateHideCompletedTodosSettings(!swcHideCompletedTodos.isChecked)
            }
        }
    }
}