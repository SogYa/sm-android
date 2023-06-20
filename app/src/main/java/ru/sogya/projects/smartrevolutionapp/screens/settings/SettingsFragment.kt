package ru.sogya.projects.smartrevolutionapp.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentSettingsBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.LogOutDialogFragment
import ru.sogya.projects.smartrevolutionapp.models.Settings

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings),
    SettingsAdapter.OnSettingsClickListenner {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var adapter: SettingsAdapter
    private val vm: SettingsVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.settingsRecyclerView.layoutManager = layoutManager
        adapter = SettingsAdapter(this)
        binding.settingsRecyclerView.adapter = adapter
        adapter.updateSettingsList(Settings.settingsList)

    }

    override fun onClick(settings: Settings) {
        when (settings.id) {
            Settings.LOG_OUT -> {
                LogOutDialogFragment().show(childFragmentManager, LogOutDialogFragment().tag)
            }
            else -> findNavController().navigate(settings.navigationId!!)
        }

    }
}