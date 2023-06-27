package ru.sogya.projects.smartrevolutionapp.screens.ticket.ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.AndroidEntryPoint
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentTicketBinding

@AndroidEntryPoint
class TicketFragment : Fragment(R.layout.fragment_ticket) {
    private lateinit var binding: FragmentTicketBinding
    private val vm: TicketVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val COLOR_YELLOW = "#FFFF00"
        private const val COLOR_RED = "#FF0000"
        private const val COLOR_GREEN = "#00FF00"
    }

    override fun onStart() {
        super.onStart()
        vm.getLoadingLiveData().observe(viewLifecycleOwner) {
            binding.loadingView.visibility = it
        }
        vm.getTicketLiveData().observe(viewLifecycleOwner) {
            if (it.ticketDesc != "") {
                binding.descriptionBlock.visibility = VISIBLE
                binding.textViewTicketDesc.text = "Комментарий: ${it.ticketDesc}"
            }
            binding.textViewTicketId.text = it.ticketId
            binding.textViewTicketDevice.text = "Устройство: ${it.ticketDevice}"
            binding.textViewTicketZone.text = "Зона: ${it.ticketZone}"
            binding.textViewTicketStatus.text = "Статус: ${it.ticketStatus}"
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticketId = arguments?.getString(Constants.TICKET_ID_KEY)
        ticketId?.let {
            vm.getTicket(it, object : MyCallBack<Boolean> {
                override fun error(error: String) {
                    super.error(error)
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}