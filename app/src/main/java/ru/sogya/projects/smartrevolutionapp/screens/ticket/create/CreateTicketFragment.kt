package ru.sogya.projects.smartrevolutionapp.screens.ticket.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.AndroidEntryPoint
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentCreateTicketBinding
import ru.sogya.projects.smartrevolutionapp.dialogs.TicketCreatedDialogFragment
@AndroidEntryPoint
class CreateTicketFragment : Fragment(R.layout.fragment_create_ticket) {
    private lateinit var binding: FragmentCreateTicketBinding
    private val vm: CreateTicketVM by viewModels()
    private val deviceList = arrayListOf("Camera", "Door", "Chair", "Electric cat")
    private val zoneList = arrayListOf("Bedroom", "Kitchen", "Dungeon gym")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vm.getLoadingLiveData().observe(viewLifecycleOwner) {
            binding.loadingView.visibility = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinnerDeviceAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, deviceList)
        spinnerDeviceAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        val spinnerZonesAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, zoneList)
        spinnerZonesAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        binding.spinnerDevices.adapter = spinnerDeviceAdapter
        binding.spinnerZones.adapter = spinnerZonesAdapter

        binding.buttonCreateTicket.setOnClickListener {
            val device = binding.spinnerDevices.selectedItem.toString()
            val zone = binding.spinnerZones.selectedItem.toString()
            val description: String = binding.editTextDescription.text.toString()

            if (device != "" && zone != "") {
                vm.createTicket(
                    device = device,
                    zone = zone,
                    description = description,
                    object : MyCallBack<Boolean>, TicketCreatedDialogFragment.TicketClickListener {
                        override fun data(t: Boolean) {
                            super.data(t)
                            val dialog = TicketCreatedDialogFragment(this)
                            dialog.show(childFragmentManager, dialog.tag)
                        }

                        override fun error(error: String) {
                            super.error(error)
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }

                        override fun onCloseClick() {
                            findNavController().popBackStack()
                        }
                    })
            } else {
                Toast.makeText(context, "Some fields are not filled in", Toast.LENGTH_SHORT).show()
            }
        }
    }
}