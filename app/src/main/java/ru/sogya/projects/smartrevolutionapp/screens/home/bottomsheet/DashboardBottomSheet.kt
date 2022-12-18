package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentBottomsheetDashboardsBinding
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding.StateAddingFragment


class DashboardBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomsheetDashboardsBinding
    private val fragmentList = arrayListOf(
        GroupAddingFragment(), StateAddingFragment()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomsheetDashboardsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPagerWithTabLayout()
        TabLayoutMediator(binding.tabLayout, binding.dashboardViewPager) { tab, position ->
            val tabNames =
                listOf(getString(R.string.tab_layout_groups), getString(R.string.tab_layout_states))
            tab.text = tabNames[position]
        }.attach()
        val dialog = Dialog(requireContext())
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
    }

    private fun setupViewPagerWithTabLayout() {
        val pagerAdapter = ViewPagerAdapter(this, fragmentList)
        binding.dashboardViewPager.adapter = pagerAdapter
    }


}