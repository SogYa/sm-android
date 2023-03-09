package ru.sogya.projects.smartrevolutionapp.screens.states.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sogya.domain.utils.Constants
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentStateSensorBinding

class SensorFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentStateSensorBinding
    private val vm: SensorVM by viewModels()
    private val list =
        arrayListOf(
            Entry(1f, 5f), Entry(2f, 6f),
            Entry(3f, 1f), Entry(4f, 10f)
        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStateSensorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        vm.getStateLiveData().observe(viewLifecycleOwner) {
            binding.stateType.text = it.attributesDomain?.friendlyName
            binding.sensorResult.text = buildString {
                append(it.state)
                append(it.attributesDomain?.unitOfMeasurement)
            }
            val libeDataSet = LineDataSet(list, it.attributesDomain?.friendlyName.toString())
            val listData = LineData(libeDataSet)
            binding.sensorChart.data = listData
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val stateId = arguments?.getString(Constants.STATE_ID).toString()
        vm.getState(stateId)
    }
}