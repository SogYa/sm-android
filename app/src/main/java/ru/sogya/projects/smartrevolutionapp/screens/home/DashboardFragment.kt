package ru.sogya.projects.smartrevolutionapp.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentDashboardBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.LogOutDialogFragment
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.DashboardBottomSheet

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private lateinit var binding: FragmentDashboardBinding
    private val vm: DashboardVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            DashboardBottomSheet().show(childFragmentManager, LogOutDialogFragment().tag)
        }
    }

    override fun onStart() {
        super.onStart()
        vm.loadingViewLiveData.observe(viewLifecycleOwner) {
            binding.loadingView.visibility = it
        }
    }

}