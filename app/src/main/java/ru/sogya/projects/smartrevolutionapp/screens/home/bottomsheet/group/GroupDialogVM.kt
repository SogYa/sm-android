package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.usecases.databaseusecase.groups.InsertGroupUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import kotlinx.coroutines.launch
import ru.sogya.projects.smartrevolutionapp.app.App


class GroupDialogVM : ViewModel() {
    private val repository = App.getRoom()
    private val insertGroupUseCase = InsertGroupUseCase(repository)
    private val sharedPreferencesRepository = App.getSharedPreferncesRepository()
    private val getStringPrefsUseCase = GetStringPrefsUseCase(sharedPreferencesRepository)

    fun createNewGroup(groupTag: String, groupDesc: String?, myCallBack: MyCallBack<Boolean>) {
        val ownerId = getStringPrefsUseCase.invoke(Constants.SERVER_URI)
        val groupState = StateGroupDomain(0, ownerId, groupTag, groupDesc.toString())

        viewModelScope.launch {
            val job = launch { insertGroupUseCase.invoke(groupState) }
            job.join()
            myCallBack.data(true)
        }
    }
}