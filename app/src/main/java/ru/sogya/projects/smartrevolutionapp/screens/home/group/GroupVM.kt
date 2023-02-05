package ru.sogya.projects.smartrevolutionapp.screens.home.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.usecases.databaseusecase.groups.DeleteGroupUseCase
import com.sogya.domain.usecases.databaseusecase.groups.GetAllGroupByOwnerUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App


class GroupVM : ViewModel() {
    private var groupsLiveData: LiveData<List<StateGroupDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)
    private val getAllGroupByOwnerUseCase = GetAllGroupByOwnerUseCase(repository)
    private val deleteGroupUseCase = DeleteGroupUseCase(repository)

    init {
        val ownerId = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        groupsLiveData = getAllGroupByOwnerUseCase.invoke(ownerId)
    }

    fun deleteGroup(groupId: Int) {
        viewModelScope.launch {
            deleteGroupUseCase.invoke(groupId)
        }
    }

    fun getGroupsLiveData() = groupsLiveData
}