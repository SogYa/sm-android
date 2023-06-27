package ru.sogya.projects.smartrevolutionapp.screens.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Circle
import com.yandex.mapkit.map.CameraPosition
import dagger.hilt.android.AndroidEntryPoint
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentMapBinding
@AndroidEntryPoint
class MapFragment : Fragment(R.layout.fragment_map) {
    private lateinit var binding: FragmentMapBinding
    private val vm: MapVM by viewModels()
    private lateinit var map: com.yandex.mapkit.map.Map


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        vm.getZonesLiveData().observe(viewLifecycleOwner) {
            vm.getPointsFromZones(it)
        }
        vm.getGeoPointLiveData().observe(viewLifecycleOwner) { points ->
            points.forEach {
                val circle = Circle(it, 15f)
                map.mapObjects.addCircle(circle, Color.RED, 2f, Color.YELLOW)
            }
            map.move(CameraPosition(points[0], 18f, 0f, 0f))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MapKitFactory.initialize(requireContext())
        map = binding.mapview.map

        binding.zoomIn.setOnClickListener {
            map.move(
                CameraPosition(map.cameraPosition.target, map.cameraPosition.zoom + 1, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 1f), null
            )
        }
        binding.zoomOut.setOnClickListener{
            map.move(
                CameraPosition(map.cameraPosition.target, map.cameraPosition.zoom - 1, 0f, 0f),
                Animation(Animation.Type.SMOOTH, 1f), null
            )
        }
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        binding.mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }
}