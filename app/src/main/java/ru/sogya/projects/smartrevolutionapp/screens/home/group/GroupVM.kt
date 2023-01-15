package ru.sogya.projects.smartrevolutionapp.screens.home.group

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.utils.Constants
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.usecases.databaseusecase.groups.DeleteGroupUseCase
import com.sogya.domain.usecases.databaseusecase.groups.GetAllGroupByOwnerUseCase
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class GroupVM : ViewModel() {
    private var groupsLiveData: LiveData<List<StateGroupDomain>> = MutableLiveData()
    private val repository = App.getRoom()
    private val getAllGroupByOwnerUseCase = GetAllGroupByOwnerUseCase(repository)
    private val deleteGroupUseCase = DeleteGroupUseCase(repository)


    init {
        val ownerId = SPControl.getInstance().getStringPrefs(Constants.SERVER_URI)
        groupsLiveData = getAllGroupByOwnerUseCase.invoke(ownerId)
    }

    fun deleteGroup(groupId: Int){
        viewModelScope.launch {
            deleteGroupUseCase.invoke(groupId)
        }
    }

    fun getGroupsLiveData() = groupsLiveData
}