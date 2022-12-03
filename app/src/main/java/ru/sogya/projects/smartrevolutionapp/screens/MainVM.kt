package ru.sogya.projects.smartrevolutionapp.screens

import androidx.lifecycle.ViewModel
import com.sogya.data.utils.Constants
import ru.sogya.projects.smartrevolutionapp.needtoremove.SPControl

class MainVM : ViewModel() {

    fun logOut() {
        SPControl.getInstance().updatePrefs(Constants.AUTH_TOKEN, "")
        SPControl.getInstance().updatePrefs(Constants.URI, "")
        SPControl.getInstance().updatePrefs(Constants.TEST_MODE, false)
    }
}