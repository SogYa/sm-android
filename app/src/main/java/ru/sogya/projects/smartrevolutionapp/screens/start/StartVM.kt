package ru.sogya.projects.smartrevolutionapp.screens.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.data.utils.Constants
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class StartVM : ViewModel() {
    private val navigationLiveData = MutableLiveData<Int>()

    init {

        if (isAuth()) {
            navigationLiveData.value = R.id.action_startFragment_to_homeFragment
        } else
            navigationLiveData.value = R.id.action_startFragment_to_authFragment

    }

    fun getNavLiveData() = navigationLiveData

    private fun isAuth(): Boolean {
        return SPControl.getInstance().getStringPrefs(Constants.AUTH_TOKEN).isNotEmpty()
    }
}