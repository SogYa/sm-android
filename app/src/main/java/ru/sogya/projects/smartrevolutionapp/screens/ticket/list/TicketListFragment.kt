package ru.sogya.projects.smartrevolutionapp.screens.ticket.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sogya.domain.models.TicketDomain
import com.sogya.domain.utils.Constants
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentTicketListBinding

class TicketListFragment : Fragment(R.layout.fragment_ticket_list),
    TicketAdapter.OnTicketClickListener {
    private lateinit var binding: FragmentTicketListBinding
    private val vm: TicketListVM by viewModels()
    private val adapter = TicketAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vm.getTicketsLiveData().observe(viewLifecycleOwner) {
            adapter.updateList(it)
            binding.ticketsHint.visibility = GONE
        }
        vm.getErrorLiveData().observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ticketsRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        binding.ticketsRecyclerView.layoutManager = layoutManager

        binding.addTicketButton.setOnClickListener {
            findNavController().navigate(R.id.action_ticketListFragment_to_createTicketFragment)
        }

    }

    override fun onClick(ticketDomain: TicketDomain) {
        val bundle = Bundle()
        bundle.putString(Constants.TICKET_ID_KEY, ticketDomain.ticketId)
        findNavController().navigate(
            R.id.action_ticketListFragment_to_ticketFragment,
            bundle
        )
    }

    override fun onLongClick(ticketDomain: TicketDomain) {
        //Вызов диалога отмены тикета тикета
        TODO("Not yet implemented")
    }
}