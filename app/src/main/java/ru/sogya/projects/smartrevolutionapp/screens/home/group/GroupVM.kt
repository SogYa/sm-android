package ru.sogya.projects.smartrevolutionapp.screens.home.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.usecases.databaseusecase.groups.DeleteGroupUseCase
import com.sogya.domain.usecases.databaseusecase.groups.GetAllGroupByOwnerUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.workers.EventWorker
import ru.sogya.projects.smartrevolutionapp.workers.GetZonesWorker
import java.util.concurrent.TimeUnit


class GroupVM : ViewModel() {
    private var groupsLiveData: LiveData<List<StateGroupDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    private val getAllGroupByOwnerUseCase = GetAllGroupByOwnerUseCase(repository)
    private val deleteGroupUseCase = DeleteGroupUseCase(repository)

    init {
        val updateStatesWork = PeriodicWorkRequestBuilder<EventWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES,
            flexTimeInterval = 15,
            flexTimeIntervalUnit = TimeUnit.MINUTES
        )
            .build()
        val getZonesWorker = OneTimeWorkRequestBuilder<GetZonesWorker>()
            .build()
        WorkManager.getInstance(App.getAppContext())
            .enqueue(
                listOf(updateStatesWork, getZonesWorker)
            )
    }

    init {
        val ownerId = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        groupsLiveData = getAllGroupByOwnerUseCase.invoke(ownerId)
    }

    fun deleteGroup(groupId: Int) {
        viewModelScope.launch {
            deleteGroupUseCase.invoke(groupId)
        }
    }

    fun getGroupsLiveData(): LiveData<List<StateGroupDomain>> = groupsLiveData
}