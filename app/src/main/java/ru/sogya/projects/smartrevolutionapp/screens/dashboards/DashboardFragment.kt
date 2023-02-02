package ru.sogya.projects.smartrevolutionapp.screens.dashboards

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sogya.domain.utils.Constants
import com.sogya.domain.models.StateDomain
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentDashboardBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.DeleteItemDialogFragment
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding.DashboardBottomSheet
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding.StateAdapter

class DashboardFragment : Fragment(R.layout.fragment_dashboard), StateAdapter.OnStateClickListener,
    DeleteItemDialogFragment.DialogFragmentListener {
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
        val bottomSheet = DashboardBottomSheet()
        val bundle = Bundle()
        val groupId = arguments?.getInt(Constants.GROUP_ID)
        bundle.putInt(Constants.GROUP_ID, groupId!!)
        binding.addButton.setOnClickListener {
            bottomSheet.arguments = bundle
            bottomSheet
                .show(childFragmentManager, DashboardBottomSheet().tag)
        }
        vm.getGroupStates(groupId)

        binding.statesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StateAdapter(this)
        binding.statesRecyclerView.adapter = adapter
        binding.statesRecyclerView.itemAnimator = null
    }

    override fun onResume() {
        super.onResume()
        vm.getItemsLiveDat().observe(viewLifecycleOwner) {
            adapter.updateStatesList(it)
            binding.loadingView.visibility = GONE
            if (it.isEmpty()) {
                binding.dashboardHint.visibility = VISIBLE
            } else {
                binding.dashboardHint.visibility = GONE
            }
            Log.d("LiveDataState", it.toString())
        }
    }

    override fun onClick(stateDomain: StateDomain) {
        val dialog = DeleteItemDialogFragment(this)
        val arguments = Bundle()
        arguments.putString(STATE_ID, stateDomain.entityId)
        dialog.arguments = arguments
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun positiveButtonClicked(stateId: String) {
        vm.deleteState(stateId)
    }

    companion object {
        private const val STATE_ID = "id"
    }

    override fun onSwitchStateChanged(stateId: String, switchState: String) {
        vm.callSwitchService(stateId,switchState)
    }
}