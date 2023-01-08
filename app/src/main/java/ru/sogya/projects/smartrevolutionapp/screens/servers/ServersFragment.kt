package ru.sogya.projects.smartrevolutionapp.screens.servers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.sogya.data.utils.Constants
import com.sogya.domain.models.ServerStateDomain
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentServersBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.DeleteServerDialogFragment
import ru.sogya.projects.smartrevolutionapp.dialogs.SelectServerDialogFragment

class ServersFragment : Fragment(R.layout.fragment_servers), ServersAdapter.OnServerClickListener,
    SelectServerDialogFragment.SelectDialogFragmentListener,DeleteServerDialogFragment.DeleteDialogFragmentListener {

    private lateinit var binding: FragmentServersBinding
    private val vm: ServersVM by viewModels()
    private val adapter = ServersAdapter(this)
    private val serverId = Bundle()
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
        binding.addServer.setOnClickListener {
            findNavController().navigate(R.id.action_serversFragment_to_authFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        vm.getServerLiveData().observe(viewLifecycleOwner) {
            adapter.updateList(it)
            if (it.isEmpty())
                binding.serverHint.visibility = VISIBLE
            else
                binding.serverHint.visibility = GONE
        }
    }

    override fun onClick(server: ServerStateDomain) {
        val dialog = SelectServerDialogFragment(this)
        serverId.putString(Constants.SERVER_URI, server.serverUri)
        dialog.arguments = serverId
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onLongClick(server: ServerStateDomain) {
        serverId.putString(Constants.SERVER_URI, server.serverUri)
        val dialog = DeleteServerDialogFragment(this)
        dialog.arguments = serverId
        dialog.show(childFragmentManager,dialog.tag)
    }

    override fun onClickSelect(serverID: String?) {
        vm.getServer(serverID.toString())
        findNavController().navigate(
            R.id.action_serversFragment_to_homeFragment,
            bundleOf(),
            navOptions {
                popUpTo(R.id.nav_graph) {
                    inclusive = true
                }
                launchSingleTop = true
            })
    }

    override fun onClickDelete(serverID: String?) {
        vm.removeServer(serverID.toString())
    }
}