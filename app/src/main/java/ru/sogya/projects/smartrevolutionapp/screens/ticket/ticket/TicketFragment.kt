package ru.sogya.projects.smartrevolutionapp.screens.ticket.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentTicketBinding

class TicketFragment : Fragment(R.layout.fragment_ticket) {
    private lateinit var binding: FragmentTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }
}