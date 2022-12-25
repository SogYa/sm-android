package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.stateadding

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
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentAddStatesBinding

class StateAddingFragment : Fragment(R.layout.fragment_add_states),
    StateAdapter.OnStateClickListener {
    private val vm: StateAddingVM by viewModels()
    private lateinit var adapter: StateAdapter
    private lateinit var binding: FragmentAddStatesBinding
    private lateinit var state: StateDomain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStatesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.statesRecyclerView.layoutManager = layoutManager
        adapter = StateAdapter(this)
        binding.statesRecyclerView.adapter = adapter

        vm.loadingViewLiveData.observe(viewLifecycleOwner) {
            binding.loadingView.visibility = it

        }
        binding.addFub.setOnClickListener {
            vm.addStatesToDataBase(state)
        }
    }

    override fun onStart() {
        super.onStart()
        vm.statesLiveData.observe(viewLifecycleOwner) {
            adapter.updateStatesList(it)
            Log.d("List", it.toString())
            state = it[0]
        }
    }


    override fun onClick(stateDomain: StateDomain) {
        Toast.makeText(context, "Coming soon", Toast.LENGTH_SHORT).show()
    }
}