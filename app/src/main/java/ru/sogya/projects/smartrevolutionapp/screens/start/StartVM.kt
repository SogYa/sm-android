package ru.sogya.projects.smartrevolutionapp.screens.start

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sogya.domain.utils.Constants
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class StartVM : ViewModel() {
    private val navigationLiveData = MutableLiveData<Int>()

    init {
        if (isFirebaseAuth()) {
            if (isAuth()) {
                if (SPControl.getInstance().getBoolPrefs(Constants.PREFS_IS_LOCKED)) {
                    navigationLiveData.value = R.id.action_startFragment_to_lockFragment
                } else {
                    navigationLiveData.value = R.id.action_startFragment_to_homeFragment
                }
            } else {
                navigationLiveData.value = R.id.action_startFragment_to_serversFragment
            }
        } else {
            navigationLiveData.value = R.id.action_startFragment_to_firebaseAuthFragment
        }

    }

    fun getNavLiveData() = navigationLiveData

    private fun isFirebaseAuth(): Boolean {
        return SPControl.getInstance().getBoolPrefs(Constants.IS_FIREBASE_AUTH)
    }

    private fun isAuth(): Boolean {
        return SPControl.getInstance().getStringPrefs(Constants.AUTH_TOKEN).isNotEmpty()
    }
}