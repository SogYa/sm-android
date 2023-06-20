package ru.sogya.projects.smartrevolutionapp.dialogs.networkconnection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.usecases.network.GetNetworkStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkConnectionVM @Inject constructor(
    getNetworkStateUseCase: GetNetworkStateUseCase
) : ViewModel() {
    private val networkStatusLiveData = getNetworkStateUseCase()

    fun getNetworkStatusLiveData(): LiveData<Boolean> = networkStatusLiveData
}