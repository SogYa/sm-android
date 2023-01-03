package ru.sogya.projects.smartrevolutionapp.screens

import androidx.lifecycle.ViewModel
import com.sogya.data.utils.Constants
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class MainVM : ViewModel() {

    fun logOut() {
        SPControl.getInstance().updatePrefs(Constants.AUTH_TOKEN, "")
        SPControl.getInstance().updatePrefs(Constants.SERVER_URI, "")
        SPControl.getInstance().updatePrefs(Constants.TEST_MODE, false)
        SPControl.getInstance().updatePrefs(Constants.SERVER_NAME, "")
        SPControl.getInstance().updatePrefs(Constants.PREFS_APPLOCK_PINCODE, "")
    }
}