package ru.sogya.projects.smartrevolutionapp.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sogya.domain.models.StateDomain
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentDashboardBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.DeleteItemDialogFragment
import ru.sogya.projects.smartrevolutionapp.screens.MainActivity
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.DashboardBottomSheet
import ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding.StateAdapter
import ru.sogya.projects.smartrevolutionapp.utils.VisibilityStates

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
        binding.addButton.setOnClickListener {
            DashboardBottomSheet().show(childFragmentManager, DashboardBottomSheet().tag)
        }
        (activity as MainActivity).getServerState()
        binding.statesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = StateAdapter(this)
        binding.statesRecyclerView.adapter = adapter
        binding.statesRecyclerView.itemAnimator = null
        binding.loadingView.visibility = VisibilityStates.GONE.visibility
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false

        }
    }

    override fun onResume() {
        super.onResume()
        println("RESUME")
        vm.loadingViewLiveData.observe(viewLifecycleOwner) {

        }
        vm.getItemsLiveDat().observe(viewLifecycleOwner) {
            adapter.updateStatesList(it)
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

    override fun onStop() {
        super.onStop()
        println("STOP")
    }

    override fun onPause() {
        super.onPause()
        println("PAUSE")
    }
}