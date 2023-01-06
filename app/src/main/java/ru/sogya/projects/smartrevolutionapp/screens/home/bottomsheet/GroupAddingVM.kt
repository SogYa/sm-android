package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.data.utils.Constants
import com.sogya.data.utils.MyCallBack
import com.sogya.domain.models.StateDomain
import com.sogya.domain.usecases.InsertOneStateUseCase
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class GroupAddingVM : ViewModel() {
    private val repository = App.getRoom()
    private val insertStateUseCase = InsertOneStateUseCase(repository)
    fun createNewGroup(groupTag: String, groupDesc: String?, myCallBack: MyCallBack<Boolean>) {
        val ownerId = SPControl.getInstance().getStringPrefs(Constants.SERVER_URI)
        val groupState = StateDomain("group:$groupTag", groupDesc.toString(), ownerId)

        viewModelScope.launch {
            val job = launch { insertStateUseCase.invoke(groupState) }
            job.join()
            myCallBack.data(true)
        }
    }
}