package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.utils.Constants
import com.sogya.data.utils.MyCallBack
import com.sogya.domain.models.StateDomain
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.usecases.InsertOneStateUseCase
import com.sogya.domain.usecases.databaseusecase.groups.InsertGroupUseCase
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class GroupAddingVM : ViewModel() {
    private val repository = App.getRoom()
    private val insertGroupUseCase = InsertGroupUseCase(repository)
    fun createNewGroup(groupTag: String, groupDesc: String?, myCallBack: MyCallBack<Boolean>) {
        val ownerId = SPControl.getInstance().getStringPrefs(Constants.SERVER_URI)
        val groupState = StateGroupDomain(ownerId,groupTag,groupDesc.toString())

        viewModelScope.launch {
            val job = launch { insertGroupUseCase.invoke(groupState) }
            job.join()
            myCallBack.data(true)
        }
    }
}