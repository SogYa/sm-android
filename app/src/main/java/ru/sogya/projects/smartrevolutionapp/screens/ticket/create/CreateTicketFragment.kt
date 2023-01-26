package ru.sogya.projects.smartrevolutionapp.screens.ticket.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentCreateTicketBinding

class CreateTicketFragment : Fragment(R.layout.fragment_create_ticket) {
    private lateinit var binding: FragmentCreateTicketBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTicketBinding.inflate(inflater, container, false)
        return binding.root
    }
}