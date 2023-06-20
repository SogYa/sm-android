package ru.sogya.projects.smartrevolutionapp.screens.home.bottomsheet.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sogya.domain.models.StateGroupDomain
import com.sogya.domain.usecases.databaseusecase.groups.InsertGroupUseCase
import com.sogya.domain.usecases.sharedpreferences.GetStringPrefsUseCase
import com.sogya.domain.utils.Constants
import com.sogya.domain.utils.MyCallBack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GroupDialogVM @Inject constructor(
    private val insertGroupUseCase: InsertGroupUseCase,
    private val getStringPrefsUseCase: GetStringPrefsUseCase,
) : ViewModel() {
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