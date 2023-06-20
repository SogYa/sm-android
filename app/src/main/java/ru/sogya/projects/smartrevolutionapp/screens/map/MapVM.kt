package ru.sogya.projects.smartrevolutionapp.screens.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.models.ZoneDomain
import com.sogya.domain.usecases.databaseusecase.zones.GetAllZonesUseCase
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapVM @Inject constructor(
    getAllZonesUseCase: GetAllZonesUseCase,
) : ViewModel() {
    private val geoPointLiveData = MutableLiveData<List<Point>>()
    private var zonesLiveData: LiveData<List<ZoneDomain>> = MutableLiveData()

    init {
        zonesLiveData = getAllZonesUseCase.invoke()
    }

    fun getPointsFromZones(listZones: List<ZoneDomain>) {
        val listPoints = arrayListOf<Point>()
        listZones.forEach {
            listPoints.add(Point(it.latitude!!, it.longitude!!))
        }
        geoPointLiveData.value = listPoints
    }

    fun getZonesLiveData() = zonesLiveData

    fun getGeoPointLiveData(): LiveData<List<Point>> = geoPointLiveData
}