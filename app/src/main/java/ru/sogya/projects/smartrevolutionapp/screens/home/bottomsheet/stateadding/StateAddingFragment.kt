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
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.AndroidEntryPoint
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentAddStateBinding

@AndroidEntryPoint
class StateAddingFragment : Fragment(R.layout.fragment_add_state) {
    private val vm: StateAddingVM by viewModels()
    private lateinit var adapter: StateAdapter
    private lateinit var binding: FragmentAddStateBinding
    private lateinit var state: StateDomain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddStateBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.statesRecyclerView.layoutManager = layoutManager
        adapter = StateAdapter()
        binding.statesRecyclerView.adapter = adapter

        vm.getLoadingLiveData().observe(viewLifecycleOwner) {
            binding.loadingView.visibility = it
        }
        binding.addFub2.setOnClickListener {
            val checkedSet = adapter.sendCheckedSet()
            if (checkedSet.isEmpty()) {
                Toast.makeText(context, "Nothing to add(", Toast.LENGTH_SHORT).show()
            } else {
                vm.addStatesToDataBase(checkedSet, 0, object : MyCallBack<Boolean> {
                    override fun data(t: Boolean) {
                        Toast.makeText(context, "State added", Toast.LENGTH_SHORT).show()
                    }

                    override fun error() {
                        super.error()
                        Toast.makeText(context, "Nothing to add(", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

    override fun onStart() {
        super.onStart()
        vm.getStatesLiveData().observe(viewLifecycleOwner) {
            adapter.updateStatesList(it)
            Log.d("List", it.toString())
            state = it[0]
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.clearCheckedSet()
    }
}