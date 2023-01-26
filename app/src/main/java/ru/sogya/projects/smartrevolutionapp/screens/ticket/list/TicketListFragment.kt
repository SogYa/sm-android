package ru.sogya.projects.smartrevolutionapp.screens.ticket.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentTicketListBinding

class TicketListFragment: Fragment(R.layout.fragment_ticket_list) {
    private lateinit var binding: FragmentTicketListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketListBinding.inflate(inflater, container, false)
        return binding.root
    }
}