package ru.sogya.projects.smartrevolutionapp.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sogya.domain.models.StateDomain
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentDashboardBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.LogOutDialogFragment
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.DashboardBottomSheet
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding.StateAdapter

class DashboardFragment : Fragment(R.layout.fragment_dashboard), StateAdapter.OnStateClickListener {
    private lateinit var binding: FragmentDashboardBinding
    private val vm: DashboardVM by viewModels()
    private lateinit var adapter: StateAdapter

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
        binding.statesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StateAdapter(this)
        binding.statesRecyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        vm.getItemsFromLocalDB()
        vm.loadingViewLiveData.observe(viewLifecycleOwner) {
            binding.loadingView.visibility = it
        }
        vm.itemsLiveData.observe(viewLifecycleOwner) {
            adapter.updateStatesList(it)
            Log.d("ListStates", it.toString())
        }
    }

    override fun onClick(stateDomain: StateDomain) {
        Toast.makeText(context, "Coming soon..", Toast.LENGTH_SHORT).show()
    }

}