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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sogya.domain.models.StateDomain
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.Constants.STATE_ID
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentDashboardBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.DeleteItemDialogFragment
import ru.sogya.projects.smartrevolutionapp.screens.states.player.MediaPlayerFragment
import ru.sogya.projects.smartrevolutionapp.screens.states.sensor.SensorFragment

class DashboardFragment : Fragment(R.layout.fragment_dashboard),
    DashboardAdapter.OnStateClickListener,
    DeleteItemDialogFragment.DialogFragmentListener {
    private lateinit var binding: FragmentDashboardBinding
    private val vm: DashboardVM by viewModels()
    private lateinit var adapter: DashboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        val groupId = arguments?.getInt(Constants.GROUP_ID)
        bundle.putInt(Constants.GROUP_ID, groupId!!)
        vm.getGroupStates(groupId)

        binding.statesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = DashboardAdapter(this)
        binding.statesRecyclerView.adapter = adapter
        binding.statesRecyclerView.itemAnimator = null
    }

    override fun onStart() {
        super.onStart()
        vm.getItemsLiveDat().observe(viewLifecycleOwner) {
            adapter.updateStatesList(it)
            binding.loadingView.visibility = GONE
            if (it.isEmpty()) {
                binding.dashboardHint.visibility = VISIBLE
            } else {
                binding.dashboardHint.visibility = GONE
            }
        }
    }

    override fun onClick(stateDomain: StateDomain) {
        val dialogFragment: BottomSheetDialogFragment
        val arguments = Bundle()
        arguments.putString(STATE_ID, stateDomain.entityId)
        if (stateDomain.entityId.startsWith("sensor")) {
            dialogFragment = SensorFragment()
            showDialog(dialogFragment,arguments)

        } else if (stateDomain.entityId.startsWith("media_player")) {
            dialogFragment = MediaPlayerFragment()
            showDialog(dialogFragment,arguments)
        }

    }
    private fun showDialog(dialogFragment: BottomSheetDialogFragment,argument:Bundle){
        dialogFragment.arguments = argument
        dialogFragment.show(childFragmentManager, dialogFragment.tag)
    }

    override fun onLongClick(stateDomain: StateDomain) {
        val dialog = DeleteItemDialogFragment(this)
        val argument = Bundle()
        argument.putString(STATE_ID, stateDomain.entityId)
        dialog.arguments = argument
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun positiveButtonClicked(stateId: String) {
        vm.deleteState(stateId)
    }

    override fun onSwitchStateChanged(stateId: String, switchState: String) {
        vm.callSwitchService(stateId, switchState)
    }

    override fun onClickWithCommand(stateId: String, command: String) {
        vm.callMediaPLayerService(stateId, command)
    }

    override fun onSliderChangeValue(stateId: String, value: Int) {
        vm.changeCoverValue(stateId, value)
    }
}