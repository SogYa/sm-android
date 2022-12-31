package ru.sogya.projects.smartrevolutionapp.screens.servers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sogya.domain.models.ServerStateDomain
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentServersBinding

class ServersFragment : Fragment(R.layout.fragment_servers), ServersAdapter.OnServerClickListenner {
    private lateinit var binding: FragmentServersBinding
    private val vm: ServersVM by viewModels()
    private val adapter = ServersAdapter(this)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        binding.serverList.layoutManager = layoutManager
        binding.serverList.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        vm.getServerLiveData().observe(viewLifecycleOwner){
            adapter.updateList(it)
        }
    }

    override fun onClick(server: ServerStateDomain) {
        TODO("Not yet implemented")
    }
}