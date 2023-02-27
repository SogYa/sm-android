package ru.sogya.projects.smartrevolutionapp.screens.home.group

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.utils.Constants
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentDashboardBinding
import ru.sogya.projects.smartrevolutionapp.screens.MainActivity
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.group.GroupBottomSheetFragment

class GroupFragment : Fragment(R.layout.fragment_dashboard), GroupAdapter.OnGroupClickListener {
    private lateinit var binding: FragmentDashboardBinding
    private val vm: GroupVM by viewModels()
    private lateinit var adapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addButton.setOnClickListener {
            GroupBottomSheetFragment()
                .show(childFragmentManager, GroupBottomSheetFragment().tag)
        }
        (activity as MainActivity).getServerState()
        binding.statesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = GroupAdapter(this)
        binding.statesRecyclerView.adapter = adapter
        binding.statesRecyclerView.itemAnimator = null
    }

    override fun onResume() {
        super.onResume()
        vm.getGroupsLiveData().observe(viewLifecycleOwner) {
            adapter.updateGroupsList(it)
            binding.loadingView.visibility = View.GONE
            binding.dashboardHint.visibility = View.GONE

            Log.d("LiveDataGroup", it.toString())
        }
    }

    override fun onClick(stateGroupDomain: StateGroupDomain) {
        val bundle = Bundle()
        bundle.putInt(Constants.GROUP_ID, stateGroupDomain.groupId)
        Log.d("GroupId", stateGroupDomain.groupId.toString())
        findNavController().navigate(
            R.id.action_homeFragment_to_dashboardFragment,
            bundle
        )
    }

    override fun onLongClick(stateGroupDomain: StateGroupDomain) {
        TODO("Not yet implemented")
    }

}